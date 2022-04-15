/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.edas;

import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class TestSimpleJobB extends JavaProcessor {
    @Override
    public ProcessResult process(JobContext jobContext) throws Exception {
        System.out.println("TestSimpleJobB " + DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        return new ProcessResult(true, String.valueOf(2));
    }
}
