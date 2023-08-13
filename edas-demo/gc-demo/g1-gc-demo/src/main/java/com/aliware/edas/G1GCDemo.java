/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.aliware.edas;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author edas
 */
@SpringBootApplication
@NacosPropertySource(dataId = "com.alibaba.nacos.example.new.properties",
    //groupId = "xuanye",
    autoRefreshed = true)
public class G1GCDemo {
	public static void main(String[] args) {
		SpringApplication.run(G1GCDemo.class, args);
	}
}
