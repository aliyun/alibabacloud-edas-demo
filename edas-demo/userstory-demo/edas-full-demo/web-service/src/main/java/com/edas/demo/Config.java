/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.edas.demo;

import com.taobao.txc.client.aop.TxcTransactionScaner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Config {
    //定义声明式事务，要想让事务annotation感知的话，要在这里定义一下
    @Bean(name = "txcScanner")
    @ConfigurationProperties(prefix="txc.aliUser")
    public TxcTransactionScaner txcTransactionScaner() {
        //专有云用default分组
        return new TxcTransactionScaner("default");

        //xxxx填写txc的逻辑组名
//        return new TxcTransactionScaner("default","txc_test_public.1129361738553704.QD",1,"https://test-cs-gts.aliyuncs.com");
    }
}
