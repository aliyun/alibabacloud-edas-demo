/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.aliyun.schedulerx.example.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;

/**
 * @author xiaomeng.hxm
 */
@Component
public class KillJob extends JavaProcessor {

    private static final Logger logger = LoggerFactory.getLogger("schedulerx");
    private volatile boolean killed = false;

    @Override
    public ProcessResult process(JobContext context) throws Exception {
        while (!killed) {
            System.out.println("do something...");
            Thread.sleep(3000);
        }
        return new ProcessResult(true);
    }

    @Override
    public void kill(JobContext context) {
        System.out.println("start to kill");
        killed = true;
    }
}
