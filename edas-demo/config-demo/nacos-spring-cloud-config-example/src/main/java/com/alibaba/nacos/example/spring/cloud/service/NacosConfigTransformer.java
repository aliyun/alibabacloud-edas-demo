package com.alibaba.nacos.example.spring.cloud.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NacosConfigTransformer {
    @Value("nacos2.username")
    private String username;

    @Value("nacos2.password")
    private String password;

    @Value("nacos2.namespace")
    private String namespace;
}
