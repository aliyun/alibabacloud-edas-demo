/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.edas.demo;

import com.alibaba.schedulerx.worker.SchedulerxWorker;
import com.edas.demo.txc_schedulerx.ScheduleXConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Autowired
    private ScheduleXConfigProperties configProperties;

    @Bean
    SchedulerxWorker schedulerxWorker(){
        SchedulerxWorker schedulerxWorker = new SchedulerxWorker();
//        schedulerxWorker.setEndpoint(configProperties.getEndpoint());
        schedulerxWorker.setNamespace(configProperties.getNamespace());
        schedulerxWorker.setGroupId(configProperties.getGroupId());
        schedulerxWorker.setDomainName(configProperties.getDomainName());//console域名
        schedulerxWorker.setAppKey(configProperties.getAppkey());//页面

        /**---------1.2.1及以上版本设置appKey-------*/
//        schedulerxWorker.setAppKey(configProperties.getAppkey());

        /**---------1.2.1以下版本需要设置AK/SK-------*/
//        schedulerxWorker.setAliyunAccessKey("xx");
//        schedulerxWorker.setAliyunSecretKey("xxx");
        return schedulerxWorker;
    }

}
