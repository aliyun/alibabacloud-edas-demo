/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.schedulerx.example.xxljob.jobhandler;

import org.springframework.stereotype.Component;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;

/**
 * @author xiaomeng.hxm
 *
 */
@JobHandler(value="HelloJobHandler")
@Component
public class HelloJobHandler extends IJobHandler {

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        System.out.println("HelloJobHandler: " + param);
        return SUCCESS;
    }

}
