1.spring cloud体验，设置灰度规则，请求参数version，值为1，访问/consumer-echo/abc?version=1,期望返回信息中处理节点为灰度节点
  - 客户端入口见hsf-pandora-boot-consumer，com.alibaba.edas.DemoController.feign1(String, HttpServletRequest, HttpServletResponse)
  - 服务端见hsf-pandora-boot-provider：com.alibaba.edas.EchoController.echo(String, HttpServletRequest)
  - Feign客户端定义参见hsf-pandora-boot-consumer的com.alibaba.edas.FeignClientConfiguration
  - 灰度代理Filter定义参见hsf-pandora-boot-consumer的com.alibaba.edas.HsfConfig.greyProxyilter()
  - 灰度代理Filter定义参见hsf-pandora-boot-provider的com.alibaba.edas.AppConfig.greyFilter()
2.hsf体验，设置灰度规则，请求参数version，值为1，访问/hsf-echo/abc?version=1,期望返回信息中处理节点为灰度节点
  - 客户端入口见：com.alibaba.edas.SimpleController.echo(String, HttpServletRequest, HttpServletResponse)
  - 服务端见：com.alibaba.edas.HelloServiceImpl.echo(String)
  - 灰度代理Filter定义参见hsf-pandora-boot-consumer的com.alibaba.edas.HsfConfig.greyProxyilter()
