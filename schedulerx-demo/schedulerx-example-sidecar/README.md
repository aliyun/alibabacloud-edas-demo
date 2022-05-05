### 简介

SchedulerX是阿里巴巴自研的一站式分布式任务调度平台（兼容开源XXL-JOB/ElasticJob），支持Cron定时、一次性任务、任务编排、分布式跑批，具有高可用、可视化、可运维、低延时等能力。自带日志服务、监控大盘、短信报警等企业级服务。

通过该demo，**不需要对业务和镜像进行改造**，只需要在Kubernetes的deployment文件（Pod、Deployment、StatefulSet、Job等都支持）中新增一个sidecar配置，就可以将定时任务托管到SchedulerX平台，拥有白屏运维、可视化和监控报警能力。

适合非Java应用，或者不想对业务进行改造的业务，详情请参考[文章](https://developer.aliyun.com/article/894962)。

### 接入步骤

1. 该demo包含两部分  
  * **node-demo-docker**：node编写的hellodemo docker镜像，有自己的镜像可以忽略。
  * **hello-schedulerx-sidecar-demo.yaml**：通过sidecar方式接入SchedulerX的Deployment案例。
2. 配置参数，参考文章[配置sidecar](https://developer.aliyun.com/article/894962#slide-3)。
3. 接入验证参考[接入验证](https://developer.aliyun.com/article/894962#slide-4)
