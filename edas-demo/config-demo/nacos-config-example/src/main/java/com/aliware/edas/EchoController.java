/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.aliware.edas;

import java.util.Properties;

import com.alibaba.edas.acm.ConfigService;
import com.alibaba.edas.acm.listener.ConfigChangeListener;
import com.alibaba.nacos.api.config.annotation.NacosValue;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author edas
 */
@Controller
@RequestMapping("config")
public class EchoController implements InitializingBean {

    //@NacosValue(value = "${nacos.user.name.new}", autoRefreshed = true)
	private String userName;

    private String testDataId = "null";

    @RequestMapping(value = "/get", method = GET)
    @ResponseBody
	public String echo() {
		return "Hello " + userName;
	}


	@RequestMapping(value = "/getTestDataId", method = GET)
	@ResponseBody
	public String getTestDataId() {
    	return testDataId;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		ConfigService.init(new Properties());

		String group = System.getProperty("project.name", "DEFAULT_GROUP");
		String dataId = "test-data-id";
		testDataId = ConfigService.getConfig(dataId, group, 2000);
		ConfigService.addListener(dataId, group,
			new ConfigChangeListener() {

			@Override
			public void receiveConfigInfo(String configInfo) {
				// 当配置更新后，通过该回调函数将最新值返回给用户。
				// 注意回调函数中不要做阻塞操作，否则阻塞通知线程。
				testDataId = configInfo;
				System.out.println(configInfo);
			}
		});
	}
}
