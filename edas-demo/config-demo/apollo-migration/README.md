# Apollo 配置导入说明

## 前置条件
- 需要准备 Apollo 的 Server 1.7.0 及以上版本
- 所在运行环境，需要准备 JDK 1.8 的环境
- 如果在本地运行 Nacos 的程序，请参考 https://help.aliyun.com/document_detail/131052.html 配置《端云互联》工具

### 在 Apollo 中准备三份配置文件，分别为：
1. application 命名空间：
   ```properties
   spring.alibabacloud.edas.name = edas
   spring.alibabacloud.edas.age = 7
   spring.alibabacloud.edas.goodat = Micro Service Architeture
   ```
2. 共享命名空间：TEST1.common1 
   ```properties
   spring.alibabacloud.cnstack.products = edas,arms,mq,csb,schedulerx
   spring.alibabacloud.cnstack.name = CNStack
   spring.alibabacloud.cnstack.customer-count = 1000
   ```
3. 共享命名空间：TEST1.common2
   ```properties
   spring.alibabacloud.middleware.products = EDAS,HSF,SchedulerX,SpringCloud for Alibba,RocketMQ,etc
   spring.alibabacloud.middleware.name = Aliware
   ```
      
## 打包

执行 `mvn package` ，进入到 <project>/edas-demo/config-demo/apollo-migration/target 目录下，
可发现 有 'apollo-migration-0.0.1-SNAPSHOT.jar' 程序包，则表示打包成功。

## 第一步，启动 Apollo 客户端应用进程
- 首先修改 `src/main/resources/application-apollo.properties` 下对应的配置文件，调整相应的配置。
- 执行 `mvn package` 进入到 `target` 目录之后，执行 `java -Dspring.profiles.active=apollo -jar apollo-migration-0.0.1-SNAPSHOT.jar ` 启动程序。
- 访问地址 `localhost:11085/config/edas` 确保获取到了对应的值

## 第二步，启动 Nacos 进程
这一步可以直接部署到 EDAS 中，也可以在本地 IDE 中通过端云互联进行启动。如果在连不上 EDAS 注册中心的地方，直接通过 `java -jar` 启动的话，会存在连接不上的问题。
启动时注意需要加上 `-Dspring.profiles.active=nacos` 的方式，激活 Nacos 的 Profile。

> 注意：这一步无需修改对应的 Nacos 地址，端云互联插件和 EDAS 管控启动时会自动适配到正确的地址。

访问地址为 `localhost:10085/config/edas` 确保访问成功。

## 第三步，导出配置文件
进入 Apollo 控制台，点击上方导航栏的 '管理员工具' -> '配置导出'，在相应的页面直接下载到本地，同时进行压缩包的解压。

## 第四步，执行导入工具

进入 target 目录中后，执行 `java -Dloader.main=com.aliware.edas.tool.MigrateToEDAS -DAPI_DOMAIN=<API_DOMAIN> -DALIYUN_AK=<AK> -DALIYUN_SK=<SK> -DNAMESPACE_ID=<NamespaceID> -DREGION_ID=<RegionId> -cp apollo-migration-0.0.1-SNAPSHOT.jar org.springframework.boot.loader.PropertiesLauncher <ExportedDir>`
其中，各个变量在 CNStack 中获取的方式为：

- API_DOMAIN: 进入部署好的 CNStack 节点的 OPS1 机器，执行 `cat /etc/hosts | grep dncs-api` ，后可看到对应的域名全值。
- ALIYUN_AK与ALIYUN_SK: 进入到 CNStack 控制台红在，点击上方导航栏的 '企业'；选择左侧边栏的'组织管理'后，在组织列表中选择相应的组织，
  然后在右边的的信息栏中找到 '管理 Accesskey' 则可找到相应的信息。
- NAMESPACE_ID: 在任 EDAS 的页面中，可通过进入 "资源管理" -> "微服务空间"，然后在 "微服务列表中" 处选择对应希望被导入的微服务空间后，
选择第一列的 UUID (三行值分别代表: 展示名称, namespace , ID)。
- REGION_ID: 在任 EDAS 的页面中，可通过进入 "资源管理" -> "微服务空间"，然后在"微服务列表中" 第一行 "默认"，选择第一列的 第二行 (如：cn-hz-vpaas-01)。
- ExportedDir: 代表从第三步中导出的文件所在地址，注意，需要填入最末端的文件夹路径。

访问地址为 `localhost:10085/config/edas` 确保访问页面出现对应配置值。
