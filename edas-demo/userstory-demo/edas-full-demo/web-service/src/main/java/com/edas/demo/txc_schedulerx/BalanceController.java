/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.edas.demo.txc_schedulerx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class BalanceController {

    @Autowired
    BalanceCache balanceCache;

    @RequestMapping(value="/listBalance",method= RequestMethod.GET)
    public Map<String,String> listBalance(HttpServletRequest request, HttpServletResponse response, ModelMap map)  {
        return balanceCache.getBalanceMap();
    }

}
