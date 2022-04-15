/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.edas.demo.txc_schedulerx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class TransferController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    AccountRepository accountRepository;


    @RequestMapping(value="/transfer",method= RequestMethod.GET)
    public String transferConsumer(HttpServletRequest request,HttpServletResponse response,ModelMap map)  {
        String str_money = request.getParameter("money");
        try {
            accountRepository.transfer(str_money);

        }catch(Exception e){
            e.printStackTrace();
            map.addAttribute("errinfo", "余额不足转账失败，GTS事务已回滚");
        }

        //重新获取余额
        reLoadBalance(map);
        return "pay";

    }
    // http://127.0.0.1:9000/pay
    @RequestMapping("/pay")
    public String login(HttpServletRequest request,ModelMap map){
        accountRepository.resetA();
        accountRepository.resetB();

        //重新获取余额
        reLoadBalance(map);
        return "pay";
    }


    private ModelMap reLoadBalance(ModelMap map){
        String accountA = accountRepository.getMoneyA();
        String accountB = accountRepository.getMoneyB();
        map.addAttribute("accountA", accountA);
        map.addAttribute("accountB", accountB);
        return map;
    }



}
