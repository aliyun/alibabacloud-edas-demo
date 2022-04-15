/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.edas.carshop.detail;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.edas.carshop.itemcenter.Item;
import com.alibaba.edas.carshop.itemcenter.ItemService;
import com.alibaba.edas.carshop.itemcenter.Util;

/**
 * Alibaba Group EDAS. http://www.aliyun.com/product/edas
 */
public class IndexServlet extends HttpServlet {

    private static final long serialVersionUID = -112210702214857712L;
    private ServletContext servletContext;
    private static final AtomicLong count = new AtomicLong();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        servletContext = config.getServletContext();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        final ItemService itemService = (ItemService) ctx.getBean("item");
        String name = req.getParameter("name");
        Item item = itemService.getItemByName(name);
        count.incrementAndGet();
        resp.setContentType("text/html; charset=utf-8");
        resp.setHeader("Cache-control", "no-store");
        PrintWriter writer = resp.getWriter();
        StringBuilder sb = new StringBuilder();
        sb.append("<h2>===HSF RPC Info====</h2>\n").append("<br/>\n").append(item.toString());
        writer.write(sb.toString());
        writer.write("\n<h2>===Http Info====</h2>\n<br/>\n");
        writer.write(Util.httpToString(req));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}
