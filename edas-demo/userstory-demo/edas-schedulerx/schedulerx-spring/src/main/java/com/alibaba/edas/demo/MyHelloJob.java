/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.edas.demo;

import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class MyHelloJob extends JavaProcessor {
    public ProcessResult process(JobContext jobContext) throws Exception {
        System.out.println("hello schedulerx2.0! Spring Boot execute scheduler " + DateTime.now().toString("yyyy-MM"
            + "-dd HH:mm:ss"));
        return new ProcessResult(true);
    }
}
