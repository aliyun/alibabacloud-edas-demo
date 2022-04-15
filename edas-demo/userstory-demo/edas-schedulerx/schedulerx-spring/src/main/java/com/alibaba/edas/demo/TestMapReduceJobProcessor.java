/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.edas.demo;

import java.util.List;
import java.util.Map.Entry;

import com.alibaba.schedulerx.shade.com.google.common.collect.Lists;
import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.MapReduceJobProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class TestMapReduceJobProcessor extends MapReduceJobProcessor {

    public ProcessResult process(JobContext context) throws Exception {
        String taskName = context.getTaskName();
        int dispatchNum = 50;
        /*if (context.getJobParameters() != null) {
            dispatchNum = Integer.valueOf(context.getJobParameters());
        }*/
        if (isRootTask(context)) {
            System.out.println("start root task " + DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            List<String> msgList = Lists.newArrayList();
            for (int i = 0; i < dispatchNum; i++) {
                msgList.add("msg_" + i);
            }
            return map(msgList, "Level1Dispatch");
        } else if (taskName.equals("Level1Dispatch")) {
            String task = (String)context.getTask();
            Thread.sleep(2000);
            System.out.println("map task:" + task + " " + DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            return new ProcessResult(true, task);
        }
        return new ProcessResult(false);
    }

    public ProcessResult reduce(JobContext context) throws Exception {
        System.out.println("start to reduce "+ DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        for (Entry<Long, String> result : context.getTaskResults().entrySet()) {
            System.out.println("taskId:" + result.getKey() + ", result:" + result.getValue());
        }
        return new ProcessResult(true, "TestMapReduceJobProcessor.reduce");
    }
}
