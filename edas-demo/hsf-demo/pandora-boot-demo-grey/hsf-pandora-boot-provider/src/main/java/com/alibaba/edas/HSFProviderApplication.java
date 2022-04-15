/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.edas;

import com.taobao.pandora.boot.PandoraBootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class HSFProviderApplication {

    public static void main(String[] args) {
        // 启动 Pandora Boot 用于加载 Pandora 容器
        PandoraBootstrap.run(args);
        SpringApplication.run(HSFProviderApplication.class, args);
        // 标记服务启动完成,并设置线程 wait。防止用户业务代码运行完毕退出后，导致容器退出。
        PandoraBootstrap.markStartupAndWait();
    }
}
