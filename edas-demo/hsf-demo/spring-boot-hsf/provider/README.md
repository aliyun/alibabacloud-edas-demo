
## 目录结构
解压后生成以下两个子目录

* bootstrap-service，包含各中间件的使用示例代码，代码在src/main/java目录下的com.alibaba.middleware包中。
* bootstrap-start，包含web配置和示例代码。使用springmvc的代码在src/main/java目录下的com.alibaba.middleware包中，web配置和vm模板在src/main/webapp目录中。日志配置文件为src/main/webapp/WEB-INF下的logback.xml。

## 使用方式
在根目录下执行

```sh
mvn package
```

在bootstrap-start的target目录下生成war。将war部署到tomcat中即可。

在bootstrap根目录下，还提供了一个应用的快速启动脚本quickstart.sh。该脚本会下载tomcat、sar，打包应用并发布到tomcat，启动tomcat。在根目录执行下列命令即可。

```sh
bash quickstart.sh
```

## 相关链接
### Pandora Boot
* gitbook ： http://mw.alibaba-inc.com/products/pandoraboot/_book/
* 钉钉交流群 ： 11701173
* wiki ： http://gitlab.alibaba-inc.com/middleware-container/pandora-boot/wikis/home
* FAQ: http://gitlab.alibaba-inc.com/middleware-container/pandora-boot/wikis/faq

### 开发者应用中心
* 线上 ： http://start.alibaba-inc.com
* 日常 ： http://start.taobao.net
* 文档 ： http://gitlab.alibaba-inc.com/middleware-container/tomcat-web/wikis/application-center

### Logger配置

* logger配置: http://gitlab.alibaba-inc.com/middleware-container/pandora-boot/wikis/log-config

### Docker相关链接
* 如果工程有docker模板，目录是 APP-META，docker模板的说明文件是：APP-META/README.md
* docker参考说明：http://gitlab.alibaba-inc.com/middleware-container/pandora-boot/wikis/docker

### HSF
* gitbook ： http://mw.alibaba-inc.com/products/hsf/_book/
* wiki ： http://gitlab.alibaba-inc.com/middleware/hsf2-0/wikis/home
* HSF用法详细说明 ： http://gitlab.alibaba-inc.com/middleware-container/pandora-boot/wikis/spring-boot-hsf