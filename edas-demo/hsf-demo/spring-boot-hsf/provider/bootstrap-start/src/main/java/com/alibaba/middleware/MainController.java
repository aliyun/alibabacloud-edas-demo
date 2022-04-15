/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.middleware;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @RequestMapping("/")
    public String root() {
        return "Welcome to EDAS! HSF provider is running......";
    }

    @RequestMapping("/hsf")
    public String hsf() {
        return "hsf";
    }

    @RequestMapping("/checkpreload.htm")
    public @ResponseBody String checkPreload() {
        return "success";
    }
}
