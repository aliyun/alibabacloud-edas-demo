/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.middleware.hsf.consumer;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.middleware.hsf.EdasHelloService;

@Controller
public class HSFController {
    @Resource
    private EdasHelloService helloConsumer;

    @RequestMapping("/")
    public String test(){
        return "Welcome to EDAS! HSF consumer is running......";
    }

    @RequestMapping(value = "/test/")
    public @ResponseBody String invokeHsf(@RequestParam String name) {
        StringBuilder result = new StringBuilder();
        result.append(helloConsumer.sayHello(name));
        return result.toString();
    }
}
