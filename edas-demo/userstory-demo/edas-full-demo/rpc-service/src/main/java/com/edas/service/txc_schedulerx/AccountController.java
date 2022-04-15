/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.edas.service.txc_schedulerx;


import com.taobao.txc.common.TxcContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Repository
public class AccountController {

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value="/add",method= RequestMethod.GET)
    public String addMoney(HttpServletRequest request) {
        String xid = request.getHeader("xid");
        System.out.println("主事务xid="+request.getHeader("xid"));
        String str_money = request.getParameter("money");
        System.out.println("money:"+str_money);
        TxcContext.bind(xid,null);

        int total_money = jdbcTemplate.queryForObject("select money from account_information",int.class);
        System.out.println("total_money = " + total_money);
        int money = Integer.parseInt(str_money);
        if(total_money < money ){
            return "-1";
        }
        jdbcTemplate.update("update account_information set money = money - ? where id = 1",str_money);
        TxcContext.unbind();
        return "1" ;
    }

    @RequestMapping(value="/getMoney",method= RequestMethod.GET)
    public int getMoney(HttpServletRequest request){
        Integer money = jdbcTemplate.queryForObject("select money from account_information",Integer.class);
        return  money.intValue();
    }

    @RequestMapping(value="/reSetAccount",method= RequestMethod.GET)
    public int reSetAccount(HttpServletRequest request){
        jdbcTemplate.update("truncate table account_information");
        jdbcTemplate.update("insert into account_information(id,money) VALUES (1,1000)");
        return  1;
    }


}
