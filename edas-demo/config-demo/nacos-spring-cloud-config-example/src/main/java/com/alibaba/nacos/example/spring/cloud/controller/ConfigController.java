/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.nacos.example.spring.cloud.controller;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.example.spring.cloud.service.NacosConfigTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {

    @Value("${useLocalCache:false}")
    private boolean useLocalCache;

    @Autowired
    private NacosConfigTransformer configTransformer;

    /**
     * http://localhost:8080/config/get
     */
    @RequestMapping("/get")
    public boolean get() {
        return useLocalCache;
    }

    @RequestMapping("/put")
    public String put(
            @RequestParam("content") String content

    ) throws NacosException {
        configTransformer.publishConfig(
                "test-data-id",
                "DEFAULT_GROUP",
                content
        );

        return "OK";
    }
}
