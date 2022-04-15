/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.edas.demo.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description
 * @author: kim
 * @create: 2021-08-09 16:35
 **/
@RestController
public class MessageController {

    @Autowired
    private MqProducer mqProducer;

    @RequestMapping("/push")
    public void pushMessage(HttpServletRequest request, HttpServletResponse response, Model model){

        String message = request.getParameter("message");
        boolean sync = Boolean.valueOf(request.getParameter("sync"));
        if(sync){
            //同步
            mqProducer.syncSendMessage(message.getBytes());
        }else{
            //异步
            mqProducer.asyncSendMessage(message.getBytes());
        }

    }

}
