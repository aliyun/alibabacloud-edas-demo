/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibabacloud.edas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DemoController {

    @Autowired
    private RestTemplate restTemplate;

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
    public String feign1(@PathVariable String str) {
        long start = System.currentTimeMillis();

        String result = echoService.echo(str);

        long end = System.currentTimeMillis();
        return "" + start + " Consumer received." +
            "\t" + result +
            "\r\n" + end + " Consumer Return";
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
