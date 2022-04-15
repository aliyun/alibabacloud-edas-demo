/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.edas;

public class IHelloServiceImpl implements IHelloService {

    @Override
    public String sayHello(String str) {
        return "hello " + str;
    }
}
