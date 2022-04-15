/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.edas;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    @Autowired
    private HelloService helloService;

    @RequestMapping(value = "/hsf-echo/{str}", method = RequestMethod.GET)
    public String echo(@PathVariable String str, HttpServletRequest request,final HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-cache");
        String httpInfo = Util.httpToString(request);
        String rpcResponse = helloService.echo(str);
        StringBuilder sb = new StringBuilder();
        sb.append("<h2>===HSF RPC Info====</h2>\n").append("<br/>\n").append(rpcResponse);
        sb.append("\n<h2>===Http Info====</h2>\n<br/>\n");
        sb.append(httpInfo);
        return sb.toString();
    }
}
