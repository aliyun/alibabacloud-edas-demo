# 简介
阿里巴巴任务调度平台SchedulerX2.0兼容开源ElasticJob任务接口，用户不需要修改一行代码，即可以将ElasticJob任务在SchedulerX2.0平台上托管，享有低成本、免运维、可视化、报警监控等能力。

详情请参考文章：https://developer.aliyun.com/article/874803

# 接入步骤
1. 登录[SchedulerX控制台](https://schedulerx2.console.aliyun.com/)，开通服务（不要钱）。
2. 选择一个地域创建一个应用（本地接入可以选择公网地域），推荐在应用管理中开通日志服务（不要钱）。
3. 修改demo中的application.yaml，替换spring.schedulerx2下的如下配置参数，配置参数[参考](https://help.aliyun.com/document_detail/161998.html)：

  ```
  spring:
     schedulerx2:
        endpoint: ${SCHEDULERX_ENDPOINT}
        namespace: ${SCHEDULERX_NAMESPACE}
        groupId: ${SCHEDULERX_GROUPID}
        appKey: ${SCHEDULERX_APPKEY}
        regionId: ${SCHEDULERX_REGION}
        aliyunAccessKey: ${ALIYUN_AK}
        aliyunSecretKey: ${ALIYUN_SK} 
  ```
4. 运行SpringBootMain类，即可运行你的定时任务（5个以下任务免费）。

# 如何验证
1. 在SchedulerX控制台应用管理中，实例总数不为0，说明客户端接入成功。
2. 任务管理下，可以看到你的定时任务。
3. 执行列表可以看到历史执行记录。