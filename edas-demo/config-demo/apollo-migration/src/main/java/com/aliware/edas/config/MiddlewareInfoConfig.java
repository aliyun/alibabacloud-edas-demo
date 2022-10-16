package com.aliware.edas.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "spring.alibabacloud.middleware")
public class MiddlewareInfoConfig {
    private String name;

    private List products;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getProducts() {
        return products;
    }

    public void setProducts(List products) {
        this.products = products;
    }
}
