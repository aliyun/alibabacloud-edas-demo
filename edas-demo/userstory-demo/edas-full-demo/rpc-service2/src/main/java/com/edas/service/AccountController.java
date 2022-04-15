/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.edas.service;


import com.taobao.txc.common.TxcContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@Repository
public class AccountController {

    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate;


    @RequestMapping(value="/add",method= RequestMethod.GET)
    public String addMoney(HttpServletRequest request) throws IOException {
        String xid = request.getHeader("xid");
        System.out.println("MoneyController:主事务xid="+request.getHeader("xid"));
        String str_money = request.getParameter("money");
        System.out.println("money:"+str_money);
        TxcContext.bind(xid,null);

        long startTime=System.currentTimeMillis();   //获取开始时间
        jdbcTemplate.update("update account_information set money = money + ? where id = 1",str_money);
        long endTime=System.currentTimeMillis(); //获取结束时间
        System.out.println("运行时间： "+(endTime-startTime)+"ms");
        TxcContext.unbind();

        return "1" ;
    }

    @RequestMapping(value="/getMoney",method= RequestMethod.GET)
    public int getMoney(HttpServletRequest request) {
        Integer money = jdbcTemplate.queryForObject("select money from account_information",Integer.class);
        return  money.intValue();
    }

    @RequestMapping(value="/reSetAccount",method= RequestMethod.GET)
    public int reSetAccount(HttpServletRequest request){
        jdbcTemplate.update("truncate table account_information");
        jdbcTemplate.update("insert into account_information(id,money) VALUES (1,0)");
        return  1;
    }

}
