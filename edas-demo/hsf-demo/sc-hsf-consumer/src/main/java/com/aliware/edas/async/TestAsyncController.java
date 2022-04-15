/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.aliware.edas.async;

import com.taobao.hsf.tbremoting.invoke.CallbackInvocationContext;
import com.taobao.hsf.tbremoting.invoke.HSFFuture;
import com.taobao.hsf.tbremoting.invoke.HSFResponseFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.alibaba.boot.hsf.annotation.HSFConsumer;

@RestController
public class TestAsyncController {

    @HSFConsumer(serviceVersion = "1.0.0", futureMethods = {"callback", "future"})
    private AsyncEchoService asyncEchoService;


    @RequestMapping(value = "/hsf-future/{str}", method = RequestMethod.GET)
    public String testFuture(@PathVariable String str) {

        String str1 = asyncEchoService.future(str);
        String str2;
        try {
            HSFFuture hsfFuture = HSFResponseFuture.getFuture();
            str2 = (String) hsfFuture.getResponse(3000);
        } catch (Throwable t) {
            t.printStackTrace();
            str2 = "future-exception";
        }
        return "Before call getResponse -> " + str1 + ",  After getResponse ->" + str2;
    }


    @RequestMapping(value = "/hsf-future-list/{str}", method = RequestMethod.GET)
    public String testFutureList(@PathVariable String str) {
        try {

            int num = Integer.parseInt(str);

            List<String> params = new ArrayList<String>();
            for (int i = 1; i <= num; i++) {
                params.add(i + "");
            }

            List<HSFFuture> hsfFutures = new ArrayList<HSFFuture>();

            for (String param : params) {
                asyncEchoService.future(param);
                hsfFutures.add(HSFResponseFuture.getFuture());
            }


            ArrayList<String> results = new ArrayList<String>();

            for (HSFFuture hsfFuture : hsfFutures) {
                results.add((String) hsfFuture.getResponse(3000));
            }

            return Arrays.toString(results.toArray());

        } catch (Throwable t) {
            return "exception";
        }
    }


    @RequestMapping(value = "/hsf-callback/{str}", method = RequestMethod.GET)
    public String testCallback(@PathVariable String str) {

        String timestamp = System.currentTimeMillis() + "";

        CallbackInvocationContext.setContext(timestamp);
        String str1 = asyncEchoService.callback(str);
        CallbackInvocationContext.setContext(null);

        return str1 + "  " + timestamp;
    }
}
