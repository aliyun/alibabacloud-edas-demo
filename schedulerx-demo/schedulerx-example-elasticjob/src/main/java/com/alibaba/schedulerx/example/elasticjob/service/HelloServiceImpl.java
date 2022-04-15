/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.schedulerx.example.elasticjob.service;

import org.springframework.stereotype.Service;

/**
 * 
 * @author xiaomeng.hxm
 */
@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello() {
        int a=1/0;
        return "helloElasticJob";
    }

}
