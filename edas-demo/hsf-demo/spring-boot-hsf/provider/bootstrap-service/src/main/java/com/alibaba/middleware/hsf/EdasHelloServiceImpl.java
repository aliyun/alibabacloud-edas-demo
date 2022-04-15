/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.middleware.hsf;


/**
 * HSF服务的实现类,详情见 http://gitlab.alibaba-inc.com/middleware/hsf2-0/wikis/home
 *
 * @author chengxu
 */
public class EdasHelloServiceImpl implements EdasHelloService {

    @Override
    public String sayHello(String name) {
        return "Welcome to EDAS! Hello, " + name + ". (from HSF provider)";
    }
}
