/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.edas;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.alibaba.boot.hsf.annotation.HSFProvider;

@HSFProvider(serviceInterface = HelloService.class, serviceVersion = "1.0.0")
public class HelloServiceImpl implements HelloService {
    @Override
    public String echo(String string) {
        return String.format("rpc params is %s rpc service process by <span class=\"highlight\">%s</span>", string, getHost());
    }

    private String getHost() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "unknown";
        }
    }
}
