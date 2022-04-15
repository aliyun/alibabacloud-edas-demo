/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.edas;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.taobao.eagleeye.EagleEye;
import com.taobao.eagleeye.RpcContext_inner;

import feign.RequestInterceptor;

@Configuration
public class FeignClientConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            RpcContext_inner ctx = EagleEye.getRpcContext();
            if (ctx != null && ctx.getTraceId() != null) {
                requestTemplate.header("EagleEye-TraceId", ctx.getTraceId());
                requestTemplate.header("EagleEye-RpcId", ctx.getRpcId());
                //@since 1.4.8
                String userData = ctx.exportPrintableUserData();
                if (userData != null && userData.length() > 0) {
                    requestTemplate.header("EagleEye-UserData", userData);
                }
            }
        };
    }
}
