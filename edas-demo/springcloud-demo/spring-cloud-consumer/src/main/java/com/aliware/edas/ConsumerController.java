/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.aliware.edas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EchoService echoService;

    @Autowired
    private RedisAccessor accessor;


    @RequestMapping(value = "/chain", method = RequestMethod.GET)
    public String chain(@RequestParam(value = "env", required = false) String env,
                        @RequestParam(value = "service", required = false) String service) {
        accessor.incrementRequests();

        try {
            String pong = echoService.chain(env, service);

            return  ("Consumer: [" + Utils.localIp() + "] -> " + pong);
        } catch (Throwable t) {
            t.printStackTrace();
            return "Consumer: [" + Utils.localIp() + "], " +
                    "provider invoke error: " + t.getMessage();
        }
    }

    @RequestMapping(value = "/stress/start", method = RequestMethod.GET)
    public Boolean stressStart() {
        try {
            StressUtils.start();

            System.out.println("Stress started");
            return true;
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
    }

    @RequestMapping(value = "/stress/stop", method = RequestMethod.GET)
    public Boolean stressStop() {
        return StressUtils.stop();
    }

    @RequestMapping(value = "/requests", method = RequestMethod.GET)
    public String requests() {
        int result = accessor.getRequests();

        return "requests: " + result;
    }

}
