/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.aliware.edas;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.rmi.CORBA.Util;

@RestController
public class ProviderController {

    @Autowired
    private EchoService echoService;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/echo/{str}")
    public String echo(@PathVariable String str){
        long start = System.currentTimeMillis();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
        }

        long end = System.currentTimeMillis();
        return "\r\n\t" + start + " Provider received." +
            "\r\n\t\tProvider processed after sleep 1 second! Echo String: \"" + str + "\"" +
            "\r\n\t" + end + " Provider Return";
    }

    @RequestMapping(value = "/chain", method = RequestMethod.GET)
    public String chain(
            @RequestParam(value = "service", required = false)
                    String service) {
        String currentServiceName = Utils.currentServiceName();

        String localIp = Utils.localIp();
        String ipString = String.format("Provider(%s): [%s]",
                service == null ? "provider" : service, localIp);

        if (service == null || currentServiceName == null ||
                service.equalsIgnoreCase(currentServiceName)) {
            return ipString;
        }

        try {
            return ipString + " -> " + restTemplate.getForObject(
                    "http://" +service + "/chain", String.class);
        } catch (Throwable t) {
            t.printStackTrace();
            return ipString + " -> Invoke service("
                    + service + ") error: " + t.getMessage();
        }
    }

}
