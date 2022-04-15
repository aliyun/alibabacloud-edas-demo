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
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoController {

    @Autowired
    private EchoService echoService;

    @RequestMapping("/ping")
    public boolean ping() {
        try {
            return echoService.alive();

        } catch (Throwable t) {
            t.printStackTrace();
        }

        return false;
    }

    @RequestMapping(value = "/provider-echo/{str}")
    public String pingPongPing(@PathVariable String str) {
        long start = System.currentTimeMillis();

        String result = echoService.echo(str);

        String[] arrays = result.split("\r\n");

        StringBuffer sb = new StringBuffer(start + " Provider echo method received\r\n");
        for (String val : arrays) {
            sb.append("\t" + val).append("\r\n");
        }

        long end = System.currentTimeMillis();

        sb.append(end + " Provider echo method return.");

        return sb.toString();
    }

    @RequestMapping(value = "/echo/{str}")
    public String echo(@PathVariable String str,HttpServletRequest req) throws ServletException, IOException {
        long start = System.currentTimeMillis();

        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
        }

        long end = System.currentTimeMillis();
        return "\r\n\t" + start + " Provider " + getHost() + " received." +
                "\r\n\t\tProvider processed after sleep 20 ms! Echo String: \"" + str + "\"" +
                "\r\n\t\t" + Util.httpToString(req) + 
                "\r\n\t" + end + " Provider Return end";
    }

    private String getHost() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "unknown";
        }
    }
}
