/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.edas;

import com.alibaba.boot.hsf.annotation.HSFConsumer;
import com.alibaba.edas.grey.proxy.BootGreyProxyFilter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;


@Configuration
public class HsfConfig {

    @HSFConsumer(clientTimeout = 3000, serviceVersion = "1.0.0")
    private HelloService helloService;
    
    @Bean
    public FilterRegistrationBean<BootGreyProxyFilter> greyFilter(){
        FilterRegistrationBean<BootGreyProxyFilter> registrationBean 
          = new FilterRegistrationBean<>();
            
        registrationBean.setFilter(new BootGreyProxyFilter());
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registrationBean.addInitParameter("http.socket.connect.timeout", "5000");
        registrationBean.addInitParameter("http.read.timeout", "60000");
        registrationBean.addInitParameter("http.connectionrequest.timeout", "5000");
        registrationBean.addInitParameter("http.maxConnections", "1000");
        registrationBean.addInitParameter("http.default.encoding", "utf-8");
        registrationBean.addInitParameter("log.level", "debug");
        registrationBean.addUrlPatterns("/*");
            
        return registrationBean;    
    }
    
    

}
