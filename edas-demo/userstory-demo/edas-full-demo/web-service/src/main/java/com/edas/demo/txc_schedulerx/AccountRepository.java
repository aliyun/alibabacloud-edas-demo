/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.edas.demo.txc_schedulerx;


import com.taobao.txc.client.aop.annotation.TxcTransaction;
import com.taobao.txc.common.TxcContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpMethod;

@Repository
public class AccountRepository {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private BalanceCache balanceCache;

    @TxcTransaction(timeout = 1000 * 1200)
    public int transfer(String money){
        String xid = TxcContext.getCurrentXid();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("xid", xid);
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);

        restTemplate.exchange("http://rpc-service/add?money={money}", HttpMethod.GET,requestEntity,String.class,money);

        ResponseEntity<String> provider2Response = restTemplate.exchange("http://rpc-service2/add?money={money}", HttpMethod.GET,
                requestEntity,
                String.class,money);

        int ret = Integer.parseInt(provider2Response.getBody());
        if(ret == -1){
            throw new RuntimeException("error222");
        }
        return 1;
    }


    public String getMoneyA(){
        String money= restTemplate.getForObject("http://rpc-service/getMoney", String.class);
        balanceCache.addBalance("A",money);
        return money;
    }

    public String getMoneyB(){
        String money= restTemplate.getForObject("http://rpc-service2/getMoney", String.class);
        balanceCache.addBalance("B",money);
        return money;
    }

    public void resetA(){
        restTemplate.getForObject("http://rpc-service/reSetAccount", String.class);
    }

    public void  resetB(){
        restTemplate.getForObject("http://rpc-service2/reSetAccount", String.class);
    }

}
