/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.edas.boot;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.edas.demo.EchoService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@Controller
public class DemoConsumerController {

    @Reference(check = false)
    private EchoService demoService;

    @RequestMapping(value = "/", produces = "text/html; charset=utf-8")
    public ModelAndView indexPage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index.html");

        return mv;
    }

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public Boolean ping() {
        try {
            String pong = demoService.echo("ping");

            System.out.println("Service returned: " + pong);
            return pong.contains("ping");
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
    }

    @RequestMapping(value = "/consumer-echo/{str}", method = RequestMethod.GET)
    public String feign1(@PathVariable String str) {
        long start = System.currentTimeMillis();

        String result = demoService.echo(str);

        long end = System.currentTimeMillis();
        return "" + start + " Consumer received." +
            "\t" + result +
            "\r\n" + end + " Consumer Return";
    }
}
