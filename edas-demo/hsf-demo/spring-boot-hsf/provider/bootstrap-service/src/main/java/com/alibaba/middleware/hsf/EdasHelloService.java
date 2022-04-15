/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.middleware.hsf;

/**
 * HSF服务接口
 *
 * @author chengxu
 */
public interface EdasHelloService {

    /**
     * HSF服务的方法
     * @param name
     * @return 调用HSF服务返回的结果
     */
    String sayHello(String name);
}
