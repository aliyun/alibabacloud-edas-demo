/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.edas.boot;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.edas.demo.EchoService;
import com.alibaba.edas.demo.ReceiverService;

@Service(timeout = 10000)
public class ReceiverServiceImpl implements ReceiverService {

    @Reference(check = false)
    private EchoService echoService;

    @Override
    public String consumerEcho(String name) {
        long start = System.currentTimeMillis();

        String result = echoService.echo(name);

        long end = System.currentTimeMillis();
        return "" + start + " Consumer received." +
            "\t" + result +
            "\r\n" + end + " Consumer Return";
    }

    @Override
    public boolean alive() {
        return true;
    }
}
