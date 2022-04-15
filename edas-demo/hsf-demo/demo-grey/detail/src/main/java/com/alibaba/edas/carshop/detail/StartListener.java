/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.edas.carshop.detail;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.edas.carshop.itemcenter.ItemService;


/**
 * Alibaba Group EDAS. http://www.aliyun.com/product/edas
 */
public class StartListener implements ServletContextListener{
	private volatile boolean run = true;
	@Override
	public void contextInitialized( ServletContextEvent sce ) {
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext( sce.getServletContext() );
	}

	@Override
	public void contextDestroyed( ServletContextEvent sce ) {
		run = false;
	}

}
