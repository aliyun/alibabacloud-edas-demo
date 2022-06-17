/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.aliyun.schedulerx.example.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.schedulerx.common.domain.TaskStatus;
import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.MapReduceJobProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;
import com.aliyun.schedulerx.example.domain.OrderInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* 通过MapReduce模型分布式扫描订单，进行超时确认处理
* 
* @author xiaomeng.hxm
*/
public class TradeOrderJob extends MapReduceJobProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger("schedulerx");
     
    @Override
    public ProcessResult process(JobContext context) {
        String taskName = context.getTaskName();
        if (isRootTask(context)) {
            LOGGER.info("start root task");
            List<OrderInfo> orderInfos = getOrderInfos();
            return map(orderInfos, "OrderInfo");
        } else if (taskName.equals("OrderInfo")) {
            OrderInfo orderInfo = (OrderInfo)context.getTask();
            //id_10这个订单，构造一个异常（1/0会抛异常）
            if (orderInfo.getId().equals("id_10")) {
                int a = 1/0;
            }
            LOGGER.info(orderInfo.toString());
            return new ProcessResult(true, String.valueOf(orderInfo.getValue()));
        }
        return new ProcessResult(false);
    }
    
    @Override
    public ProcessResult reduce(JobContext context) throws Exception {
        Map<Long, String> allTaskResults = context.getTaskResults();
        Map<Long, TaskStatus> allTaskStatuses = context.getTaskStatuses();
        long sum = 0;
        for (Entry<Long, String> entry : allTaskResults.entrySet()) {
            // 过滤根任务
            if (entry.getKey() == 0) {
                continue;
            }
            if (allTaskStatuses.get(entry.getKey()).equals(TaskStatus.SUCCESS)) {
                sum += Integer.valueOf(entry.getValue());
            }
        }
        
        LOGGER.info("reduce: count=" + sum);
        return new ProcessResult(true, String.valueOf(sum));
    }
    
    /**
     * 获取订单列表，具体实现需要自己从数据中获取
     */
    private List<OrderInfo> getOrderInfos() {
        List<OrderInfo> orderList = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            OrderInfo orderInfo = new OrderInfo("id_" + i, i);
            orderList.add(orderInfo);
        }
        return orderList;
    }
    
}
