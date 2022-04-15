/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.edas;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "hsf-pandora-boot-consumer")
public interface EchoService {
    @RequestMapping(value = "/consumer/alive", method = RequestMethod.GET)
    boolean alive();

    @RequestMapping(value = "/consumer-echo/{str}", method = RequestMethod.GET)
    String echo(@PathVariable("str") String str);
}
