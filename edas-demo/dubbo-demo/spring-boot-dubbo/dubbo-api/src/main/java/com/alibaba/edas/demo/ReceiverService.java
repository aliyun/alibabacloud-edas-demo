/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.edas.demo;

public interface ReceiverService {

    String consumerEcho(String name);

    boolean alive();
}
