/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibabacloud.edas;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author edas
 */
@Controller
@RequestMapping("gc")
public class GCController implements InitializingBean {

	private String userName;

    @RequestMapping(value = "/start", method = GET)
    @ResponseBody
	public String start() {
    	GCUtil.startScheduling();
		return "Object generation started. ";
	}

	@RequestMapping(value = "/stop", method = GET)
	@ResponseBody
	public String stop() {
		GCUtil.stopScheduling();
		return "Object generation stopped. ";
	}


	@Override
	public void afterPropertiesSet() throws Exception {

	}
}
