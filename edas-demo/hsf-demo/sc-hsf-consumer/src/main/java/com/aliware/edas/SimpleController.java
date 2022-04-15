/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.aliware.edas;

import com.alibaba.boot.hsf.annotation.HSFConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SimpleController {


    @HSFConsumer(serviceVersion = "1.0.0")
    private EchoService echoService;

    @RequestMapping(value = "/echo/{str}", method = RequestMethod.GET)
    public String echo(@PathVariable String str) {
        return echoService.echo(str);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String echo() {
        return echoService.echo("It works man ~~");
    }
}
