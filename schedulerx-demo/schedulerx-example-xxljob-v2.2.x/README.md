### 简介

阿里巴巴任务调度平台SchedulerX2.0兼容开源XXL-JOB任务接口，支持@XxlJob新注解和@JobHandler老注解方式，用户不需要修改一行代码，即可以将XXL-JOB任务在SchedulerX2.0平台上托管，享有低成本、免运维、可视化、报警监控等能力。

详情请参考[文章](https://developer.aliyun.com/article/854993)。

### 接入步骤
1. 登录[SchedulerX控制台](https://schedulerx2.console.aliyun.com/)，开通服务（不要钱）。
2. 选择一个地域创建一个应用（本地接入可以选择公网地域），推荐在应用管理中开通日志服务（不要钱）。
3. 修改demo中的application.properties，替换spring.schedulerx2下的如下配置参数，配置参数[参考](https://help.aliyun.com/document_detail/161998.html)：

  ```
 spring.schedulerx2.endpoint={SCHEDULERX_ENDPOINT}
 spring.schedulerx2.namespace={SCHEDULERX_NAMESPACE}
 spring.schedulerx2.groupId={SCHEDULERX_GROUPID}
 spring.schedulerx2.appKey={SCHEDULERX_APPKEY}
  ```
4. 在SchedulerX控制台，任务管理，创建任务，任务类型选择xxljob（5个以下任务免费）。
5. 运行XxlJobExecutorApplication类，即可运行你的定时任务。

### 如何验证
1. 在SchedulerX控制台应用管理中，实例总数不为0，说明客户端接入成功。
2. 执行列表可以看到历史执行记录。
3. 日志服务可以看到任务运行的日志。
