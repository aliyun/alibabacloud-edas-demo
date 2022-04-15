/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.edas.carshop.itemcenter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Alibaba Group EDAS. http://www.aliyun.com/product/edas
 */
public class IndexServlet extends HttpServlet {

	private static final long serialVersionUID = -112210702214857712L;

	@Override
	public void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
	    resp.setHeader("Cache-control", "no-store");
        resp.setContentType("text/html");
        resp.getWriter().write(Util.httpToString(req));
	}
	
	@Override
	protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
		doGet(req, resp);
	}

}
