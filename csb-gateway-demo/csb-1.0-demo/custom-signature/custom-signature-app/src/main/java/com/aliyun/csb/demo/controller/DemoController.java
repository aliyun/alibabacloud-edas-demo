package com.aliyun.csb.demo.controller;

import com.alibaba.csb.sdk.HttpCallerException;
import com.alibaba.csb.sdk.HttpParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csb.sdk.HttpCaller;


import java.util.Map;

/**
 * @author 泊闻
 */
@RestController
public class DemoController {
    private static final Logger log = LoggerFactory.getLogger(DemoController.class);

    @Value("${csb.broker.address}")
    private String brokerAddress;

    @Value("${csb.accessKey}")
    private String accessKey;

    @Value("${csb.secretKey}")
    private String secretKey;

    @Value("${sign.secret}")
    private String secret;


    @RequestMapping("/invokeCSB")
    public String invokeCSB(@RequestParam Map<String, String> params) {
        HttpParameters.Builder builder = HttpParameters.newBuilder();

        builder.requestURL(brokerAddress + "/CSB")
                .api("test")
                .version("1.0.0")
                .method("get")
                .accessKey(accessKey)
                .secretKey(secretKey)
                // 这里指定生成签名使用的类
                .signImpl("com.aliyun.csb.signature.SHA256SignImpl")
                // 这里指定 broker 验签时使用的类
                .verifySignImpl("com.aliyun.csb.signature.SHA256VerifySignImpl");

        for (Map.Entry<String, String> entry: params.entrySet()) {
            builder.putParamsMap(entry.getKey(), entry.getValue());
        }

        String result = null;

        try {
            result = HttpCaller.invoke(builder.build());
        } catch (HttpCallerException e) {
            log.error("call csb error", e);
        }
        return result;
    }
}
