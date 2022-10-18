/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.aliware.edas;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.aliware.edas.config.CNStackInfoConfig;
import com.aliware.edas.config.EDASInfoConfig;
import com.aliware.edas.config.MiddlewareInfoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author edas
 */
@Controller
@RequestMapping("config")
public class EchoController {

	private String userName;

	@Autowired
    private EDASInfoConfig edas;

	@Autowired
	private CNStackInfoConfig cnstack;

	@Autowired
	private MiddlewareInfoConfig middleware;

    @RequestMapping(value = "/edas", method = GET)
    @ResponseBody
	public String edasInfo() {
		return "Hello " + JSON.toJSONString(edas);
	}

	@RequestMapping(value = "/cnstack", method = GET)
	@ResponseBody
	public String cnstackInfo() {
		return "Hello " + JSON.toJSONString(cnstack);
	}
	@RequestMapping(value = "/middleware", method = GET)
	@ResponseBody
	public String middlewareInfo() {
		return "Hello " + JSON.toJSONString(middleware);
	}
}
