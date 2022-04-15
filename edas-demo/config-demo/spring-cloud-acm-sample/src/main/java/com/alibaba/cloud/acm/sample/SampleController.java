/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.cloud.acm.sample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sample")
@RefreshScope
class SampleController {

    @Value("${user.name}")
    String userName;

    @Value("${user.id}")
    String userId;

    @RequestMapping("/edas")
    public String simple() {
        return "Hello " + userName + ",  your number is " + userId;
    }
}
