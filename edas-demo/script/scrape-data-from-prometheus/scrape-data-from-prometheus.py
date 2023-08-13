import heapq
import json
import time

from prometheus_api_client import PrometheusConnect
import datetime

PROMQL_CLUSTER_CPU_USAGE = """(1 - avg by(instance)(rate(node_cpu_seconds_total{mode="idle"}[1m]))) * 100"""
PROMQL_CLUSTER_MEM_USAGE = """(1 - avg by(instance)(node_memory_MemAvailable_bytes) / avg by(instance)(node_memory_MemTotal_bytes)) * 100"""
PROMQL_CLUSTER_DISK_READ = """sum(rate(node_disk_read_bytes_total[5m])) by (instance)"""
PROMQL_CLUSTER_DISK_WRITE = """sum(rate(node_disk_written_bytes_total[5m])) by (instance)"""
PROMQL_CLUSTER_LOAD15 = """node_load15"""

PROMQL_POD_RESTART = """kube_pod_container_status_restarts_total"""
PROMQL_POD_CPU_USAGE = """sum(rate(container_cpu_usage_seconds_total{container_name!="POD"}[1m])) by (pod_name, namespace) / sum(kube_pod_container_resource_limits_cpu_cores) by (pod_name, namespace) * 100"""
PROMQL_POD_MEM_USAGE = """sum(container_memory_working_set_bytes{container_name!="POD"}) by (pod_name, namespace) / sum(kube_pod_container_resource_limits_memory_bytes) by (pod_name, namespace) * 100"""

# 填写Prometheus URL
PROM_URL = ""
# 填写Prometheus token
PROM_AUTH_TOKEN = ""
# 以当前时间为END_TIME
END_TIME = int(time.time())
# 抓去7天范围的数据
START_TIME = END_TIME - 7 * 24 * 3600
# 数据点间隔，单位秒
PROM_DATA_INTERNAL_STEP = 300
# POD相关信息，输出TOP N
POD_INFO_TOP_N = 9999999


def get_end_datetime() -> datetime.datetime:
    return datetime.datetime.fromtimestamp(END_TIME)


def get_start_datetime() -> datetime.datetime:
    return datetime.datetime.fromtimestamp(START_TIME)


def query_range_prometheus(url, query, start_time, end_time, step, token):
    headers = {'Authorization': token}
    pc = PrometheusConnect(url, headers=headers)
    return pc.custom_query_range(query, start_time, end_time, step)


def query_prometheus(url: str, token: str, prom_ql: str) -> list:
    headers = {'Authorization': token}
    pc = PrometheusConnect(url, headers=headers)
    result = pc.custom_query(prom_ql)
    return result


def scrape_cluster_data(prom_url: str, prom_token: str) -> dict:
    data = {}
    data["load15"] = query_and_process_data(prom_url, prom_token, PROMQL_CLUSTER_LOAD15)
    data["节点CPU使用率"] = query_and_process_data(prom_url, prom_token, PROMQL_CLUSTER_CPU_USAGE)
    data["节点内存使用率"] = query_and_process_data(prom_url, prom_token, PROMQL_CLUSTER_MEM_USAGE)
    data["节点磁盘读取(Bytes)"] = query_and_process_data(prom_url, prom_token, PROMQL_CLUSTER_DISK_READ)
    data["节点磁盘写入(Bytes)"] = query_and_process_data(prom_url, prom_token, PROMQL_CLUSTER_DISK_WRITE)

    worker_data = {}
    for type, values in data.items():
        for instance, v_tuple in values.items():
            d = worker_data.get(instance, {})
            min, max, avg = v_tuple
            d[f"{type}-最小值"] = min
            d[f"{type}-最大值"] = max
            d[f"{type}-平均值"] = avg
            worker_data[instance] = d
    return worker_data


def query_and_process_data(prom_url: str, prom_token: str, prom_ql: str) -> dict:
    data = query_range_prometheus(prom_url, prom_ql, get_start_datetime(), get_end_datetime(), PROM_DATA_INTERNAL_STEP, prom_token)
    _, statistic_data = __extract_prom_data(data, "instance")
    return statistic_data


def __extract_prom_data(data: list, legend_key: str) -> (dict, dict):
    draw_data = {}
    statistic_data = {}
    for d in data:
        metric: dict = d["metric"]
        key = metric[legend_key]
        values: list = d["values"]
        timestamps = [datetime.datetime.fromtimestamp(int(v[0])) for v in values]
        values = [float(v[1]) for v in values]
        draw_data[key] = (timestamps, values)
        statistic_data[key] = get_min_max_avg(values)
    return draw_data, statistic_data


def get_min_max_avg(data: list) -> (float, float, float):
    min = data[0]
    max = data[0]
    sum = 0
    for d in data:
        sum += d
        if d > max:
            max = d
        if d < min:
            min = d
    avg = sum / len(data)
    return min, max, avg


def scrape_pod_data(prom_url:str, prom_token) -> any:
    pod_data = {}

    pod_data["pod-restart"] = _query_pod_restart(prom_url, prom_token)
    pod_cpu_data = _query_pod_cpu(prom_url, prom_token)
    pod_mem_data = _query_pod_mem(prom_url, prom_token)

    pod_data["Pod CPU使用率最大值"] = get_top_n_element(POD_INFO_TOP_N, 1, pod_cpu_data)
    pod_data["Pod CPU使用率平均值"] = get_top_n_element(POD_INFO_TOP_N, 2, pod_cpu_data)

    pod_data["Pod内存使用率最大值"] = get_top_n_element(POD_INFO_TOP_N, 1, pod_mem_data)
    pod_data["Pod内存使用率平均值"] = get_top_n_element(POD_INFO_TOP_N, 2, pod_mem_data)

    return pod_data


def _query_pod_restart(prom_url:str, prom_token: str) -> list:
    data = query_prometheus(prom_url, prom_token, PROMQL_POD_RESTART)
    result = []
    for d in data:
        pod = d["metric"]["pod"]
        v = int(str(d["value"][1]))
        if v == 0:
            continue
        result.append((pod, v))
    return result


def _query_pod_cpu(prom_url:str, prom_token: str) -> dict:
    data = query_range_prometheus(prom_url, PROMQL_POD_CPU_USAGE, get_start_datetime(), get_end_datetime(), PROM_DATA_INTERNAL_STEP, prom_token)
    _, statistic_data = __extract_prom_data(data, "pod_name")
    return statistic_data


def _query_pod_mem(prom_url:str, prom_token: str) -> dict:
    data = query_range_prometheus(prom_url, PROMQL_POD_MEM_USAGE, get_start_datetime(), get_end_datetime(), PROM_DATA_INTERNAL_STEP, prom_token)
    _, statistic_data = __extract_prom_data(data, "pod_name")
    return statistic_data


def get_top_n_element(n: int, value_idx:int, data: dict)->list:
    l = [(k, v) for k, v in data.items()]
    top_n = heapq.nlargest(n, l, key=lambda x: x[1][value_idx])
    return [(v[0], v[1][value_idx]) for v in top_n]


if __name__ == '__main__':
    if PROM_URL == "" or PROM_AUTH_TOKEN == "":
        raise RuntimeError("请填写Prometheus访问信息")
    cluster_data = scrape_cluster_data(PROM_URL, PROM_AUTH_TOKEN)
    with open("cluster-data.json", "w") as f:
        f.write(json.dumps(cluster_data, ensure_ascii=False))
    pod_data = scrape_pod_data(PROM_URL, PROM_AUTH_TOKEN)
    with open("pod-data.json", "w") as f:
        f.write(json.dumps(pod_data, ensure_ascii=False))