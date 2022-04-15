/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.edas.controller;

import com.alibaba.edas.boot.ReceiverServiceHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class DubboProviderController {

    @Autowired
    private ReceiverServiceHolder receiverService;

    @RequestMapping(value = "/", produces = "text/html; charset=utf-8")
    public ModelAndView indexPage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index.html");

        return mv;
    }

    @RequestMapping("/ping")
    public boolean ping(){
        try {
            return receiverService.alive();

        } catch (Throwable t) {
            t.printStackTrace();
        }

        return false;
    }

    @RequestMapping(value = "/provider-echo/{str}")
    public String pingPongPing(@PathVariable String str) {
        long start = System.currentTimeMillis();

        String result = receiverService.consumerEcho(str);

        String[] arrays = result.split("\r\n");

        StringBuffer sb = new StringBuffer(start + " Provider echo method received\r\n");
        for (String val : arrays) {
            sb.append("\t" + val).append("\r\n");
        }

        long end = System.currentTimeMillis();

        sb.append(end + " Provider echo method return.");

        return sb.toString();
    }
}
