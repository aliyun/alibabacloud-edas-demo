### 简介

当前demo用于演示任务调度采用log4j2日志组件场景下的任务处理业务日志对接，实现任务执行业务日志在控制台可视化查询。

### 接入方式（二选一）
* 常规Java工程集成log4j2：参考DEMO工程示例log4j2.xml（默认加载项）配置的调度日志组件Appender及Logger；业务任务处理时获取对应名称Logger输出业务日志即可
* SpringBoot集成log4j2：参考示例中pom依赖注释采用基于springboot日志组件，根据log4j2-spring.xml（默认加载项）配置的调度日志组件Appender及Logger；业务任务处理时获取对应名称Logger输出业务日志即可
