/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.edas.demo.txc_schedulerx;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class BalanceCache {

    private Map<String,String> map = new ConcurrentHashMap();

    public void addBalance(String key,String value){
        map.put(key,value);
    }

    public Map<String,String> getBalanceMap(){
        return map;
    }
}
