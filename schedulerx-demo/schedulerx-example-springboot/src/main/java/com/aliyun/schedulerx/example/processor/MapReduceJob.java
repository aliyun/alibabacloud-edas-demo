/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.aliyun.schedulerx.example.processor;

import java.util.List;
import java.util.Map;

<<<<<<< HEAD
=======
import com.alibaba.schedulerx.shade.org.apache.commons.lang.StringUtils;
>>>>>>> db94a56533e8f7f07c54caa0003ef8cb8c76d983
import org.springframework.stereotype.Component;

import com.alibaba.schedulerx.shade.com.google.common.collect.Lists;
import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.MapReduceJobProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;

/**
 * MapReduce任务Demo：分发50条消息，分布式并行处理
 * @author yaohui
 * @create 2022/3/16 下午7:54
 **/
@Component
public class MapReduceJob extends MapReduceJobProcessor {

    @Override
    public ProcessResult process(JobContext context) throws Exception {
        String taskName = context.getTaskName();
        int dispatchNum = 50;
<<<<<<< HEAD
        if (context.getJobParameters() != null) {
=======
        if (StringUtils.isNotEmpty(context.getJobParameters()) && StringUtils.isNumeric(context.getJobParameters())) {
>>>>>>> db94a56533e8f7f07c54caa0003ef8cb8c76d983
            dispatchNum = Integer.valueOf(context.getJobParameters());
        }
        if (isRootTask(context)) {
            System.out.println("start root task");
            List<String> msgList = Lists.newArrayList();
            for (int i = 0; i <= dispatchNum; i++) {
                msgList.add("msg_" + i);
            }
            return map(msgList, "Level1Dispatch");
        } else if (taskName.equals("Level1Dispatch")) {
            String task = (String)context.getTask();
            Thread.sleep(2000);
            return new ProcessResult(true, task);
        }

        return new ProcessResult(false);
    }

    @Override
    public ProcessResult reduce(JobContext context) throws Exception {
        for (Map.Entry<Long, String> result : context.getTaskResults().entrySet()) {
            System.out.println("taskId:" + result.getKey() + ", result:" + result.getValue());
        }
        return new ProcessResult(true, "MapReduceJob.reduce");
    }

}
