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
public class DemoController {

    @Autowired
    private EchoService echoService;

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public Boolean ping() {
        try {
            String pong = echoService.echo("ping");

            System.out.println("Service returned: " + pong);
            return pong.contains("ping");
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
    }

    @RequestMapping(value = "/consumer-echo/{str}", method = RequestMethod.GET)
    public String feign1(@PathVariable String str, HttpServletRequest request,final HttpServletResponse response) throws ServletException, IOException {
        long start = System.currentTimeMillis();

        String result = echoService.echo(str);
        
        response.setHeader("Cache-Control", "no-cache");
        String httpInfo = Util.httpToString(request);
        String rpcResponse = result;
        StringBuilder sb = new StringBuilder();
        sb.append("<h2>===Invoke echoService RPC Info====</h2>\n").append("<br/>\n").append(rpcResponse);
        sb.append("<br/>===========================================================================================<br/>");
        sb.append("\n<h2>===Client Http Info====</h2>\n<br/>\n");
        sb.append(httpInfo);
        return sb.toString();
    }

    @RequestMapping(value = "/consumer/alive", method = RequestMethod.GET)
    public boolean alive() {
        return true;
    }

    @RequestMapping(value = "/consumer-echo/feign/{str}", method = RequestMethod.GET)
    public String feign2(@PathVariable String str) {
        return echoService.echo(str) + " By feign.";
    }
}
