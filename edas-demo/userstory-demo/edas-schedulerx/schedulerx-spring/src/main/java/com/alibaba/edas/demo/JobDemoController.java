/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.edas.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobDemoController {
    private boolean switchHello = true;
    @RequestMapping("/start")
    public String hello() throws InterruptedException {
        switchHello = true;

        while (switchHello) {
            System.out.println("do some business");
            Thread.sleep(1000*10);
        }
        return "start";
    }

    @RequestMapping("/close")
    public void close(){
        switchHello = false;
        System.out.println("switch to close");
    }
}
