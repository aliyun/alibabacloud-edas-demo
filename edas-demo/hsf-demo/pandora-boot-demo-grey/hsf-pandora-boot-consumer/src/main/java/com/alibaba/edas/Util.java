/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.edas;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class Util {
    public static String httpToString(HttpServletRequest req) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        String xForwardedFor = req.getHeader("X-Forwarded-For");
        sb.append("\n<pre>\n");
        sb.append("requestURL=").append(req.getRequestURL()).append("\n")
                .append("<span class=\"highlight\">current process instance IP=").append(getIP()).append("</span>\n")
                .append("xForwardedFor=").append(xForwardedFor).append("\n")
                .append(" at ").append(new Date());
        sb.append("</pre>");
        
        sb.append("\n<br/>");
        sb.append("\n<h3>====headers===</h3>");
        sb.append("\n<pre>\n");
        Enumeration<?> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = (String) headerNames.nextElement();
            if(name.equals("x-edas-grey-proxy")) {
                sb.append("<span class=\"highlight\">");
                sb.append(name).append("=").append("转发请求的实例IP").append(req.getHeader(name)).append("</span>\n");
            }else {
                sb.append(name).append("=").append(req.getHeader(name)).append("\n");
            }
        }
        sb.append("\n</pre>");

        sb.append("\n<br/>");
        sb.append("\n<h3>====cookies===</h3>");
        sb.append("\n<pre>\n");
        Cookie[] cookies = req.getCookies();
        for (int i = 0; cookies != null && i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            sb.append(cookie.getName()).append("=").append(cookie.getValue()).append("\n");
        }
        sb.append("\n</pre>\n");
        return sb.toString();
    }
    
    public static String getIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return e.getMessage();
        }
    }
}
