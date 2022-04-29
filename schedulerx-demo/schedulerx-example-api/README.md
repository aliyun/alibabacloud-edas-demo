# 简介
此demo以Java语言为例，包含了SchedulerX的各个OpenAPI。

详细的接入文档请参考[API调用方式](https://help.aliyun.com/document_detail/176499.html)

非Java语言，可以直接在通过阿里云[OpenAPI平台](https://next.api.aliyun.com/api/schedulerx2/2019-04-30/CreateJob)自动生成

# 接入
1. sdk版本 

  通过[mvn中央仓库](https://mvnrepository.com/artifact/com.aliyun/aliyun-java-sdk-schedulerx2)获取最新版schedulerx openapi的sdk
  
2. 修改代码中的如下参数

* regionId: 所选地域的regionId，参考[地域列表](https://help.aliyun.com/document_detail/176499.html#section-0m5-2fr-ph3)
* accessKeyId: 阿里云账号的ak
* accessKeySecret: 阿里云账号的sk
* namespace: SchedulerX控制台的命名空间ID
* groupId: SchedulerX控制台的应用ID
