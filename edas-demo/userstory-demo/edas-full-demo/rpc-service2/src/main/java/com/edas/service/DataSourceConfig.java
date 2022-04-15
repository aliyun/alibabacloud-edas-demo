/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.edas.service;


import com.taobao.txc.client.aop.TxcTransactionScaner;
import com.taobao.txc.datasource.cobar.TxcDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DataSourceConfig {


    @Bean(name = "secondaryDataSource")
    @Qualifier("secondaryDataSource")
    @Primary
    @ConfigurationProperties(prefix="spring.datasource.secondary")
    public com.taobao.txc.datasource.cobar.TxcDataSource secondaryDataSource()
    {
        return new TxcDataSource();
    }

    @Bean(name = "secondaryJdbcTemplate")
    public JdbcTemplate secondaryJdbcTemplate(
            @Qualifier("secondaryDataSource") javax.sql.DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }


    //定义声明式事务，要想让事务annotation感知的话，要在这里定义一下
    @Bean(name = "txcScanner")
    @ConfigurationProperties(prefix="txc.aliUser")
    public TxcTransactionScaner txcTransactionScaner(){
        /**------------------专有云或VPC环境----------------*/

        return  new TxcTransactionScaner("default");

        /**------------------公有云需要指定接入点，否则走内网环境--------------*/
//        return  new TxcTransactionScaner("default","txc_test_public.1129361738553704.QD",1,"https://test-cs-gts.aliyuncs.com");
    }

}
