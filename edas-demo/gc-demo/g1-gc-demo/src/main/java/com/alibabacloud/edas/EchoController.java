/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibabacloud.edas;

import java.util.Properties;


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

	}
}
