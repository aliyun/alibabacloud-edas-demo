/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibabacloud.edas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author edas
 */
@SpringBootApplication
//@EnableDiscoveryClient
public class NacosProviderWithEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(NacosProviderWithEurekaApplication.class, args);
	}
}
