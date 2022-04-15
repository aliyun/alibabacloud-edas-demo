/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.schedulerx.example.elasticjob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 
 * @author xiaomeng.hxm
 */
@SpringBootApplication
@ComponentScan(value = "com.alibaba.schedulerx.example.elasticjob.*")
@ComponentScan(value = "com.alibaba.schedulerx.plugin.*")
public class SpringBootMain {
    
    public static void main(final String[] args) throws InterruptedException {
        SpringApplication.run(SpringBootMain.class, args);
    }
}
