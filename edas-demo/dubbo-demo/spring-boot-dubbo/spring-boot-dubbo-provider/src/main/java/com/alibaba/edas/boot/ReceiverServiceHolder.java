/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.edas.boot;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.edas.demo.ReceiverService;

import org.springframework.stereotype.Service;

@Service
public class ReceiverServiceHolder {

    @Reference(check = false)
    private ReceiverService receiverService;

    public boolean alive() {
        return receiverService.alive();
    }

    public String consumerEcho(String str) {
        return receiverService.consumerEcho(str);
    }
}
