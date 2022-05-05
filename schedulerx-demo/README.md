### 简介

此工程是阿里巴巴任务调度SchedulerX的官方demo工程，包含如下： 

* schedulerx-example-springboot：springboot接入的完整demo工程。
* schedulerx-example-api：java openapi的demo工程。
* schedulerx-example-elasticjob：兼容elasticjob的完整demo工程。
* schedulerx-example-xxljob：兼容xxljob的完整demo工程。
* schedulerx-example-sidecar：通过k8s sidecar方式接入schedulerx的demo示例。
* schedulerx-example-log-log4j2：通过原生log4j2收集业务日志到SchedulerX平台的demo示例。
  * demo示例中log4j2.xml配置为日志组件默认加载的配置文件
  * 也可参考示例中的注释及log4j2-spring.xml，采用基于springboot的日志依赖使用
* schedulerx-example-log-logback：通过原生logback收集业务日志到SchedulerX平台的demo示例。
