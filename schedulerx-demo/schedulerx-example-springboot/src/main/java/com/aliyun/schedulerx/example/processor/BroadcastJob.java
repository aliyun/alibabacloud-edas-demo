/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.aliyun.schedulerx.example.processor;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.alibaba.schedulerx.common.domain.TaskStatus;
import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;

/**
* 广播任务demo，分布式模型需要选择broadcast
* 
* @author xiaomeng.hxm
*/
@Component
public class BroadcastJob extends JavaProcessor {

    /**
     * 所有机器同时执行process方法
     */
    @Override
    public ProcessResult process(JobContext context) throws Exception {
        int value = new Random().nextInt(10);
        System.out.println("分片总数=" + context.getShardingNum() + ", 分片号=" + context.getShardingId() + ", "
                + "taskId=" + context.getTaskId() + ", value=" + value);
        return new ProcessResult(true, String.valueOf(value));
    }
    
    /**
     * 所有机器执行process方法之前，只有一台机器执行一次
     */
    @Override
    public void preProcess(JobContext context) {
        System.out.println("this is preProcess");
    }
    
    /**
     * 所有机器执行process之后，只有一台机器执行一次
     */
    @Override
    public ProcessResult postProcess(JobContext context) {
        System.out.println("TestBroadcastJob.postProcess");
        Map<Long, String> allTaskResults = context.getTaskResults();
        Map<Long, TaskStatus> allTaskStatuses = context.getTaskStatuses();
        int num = 0;
        for (Entry<Long, String> entry : allTaskResults.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
            if (allTaskStatuses.get(entry.getKey()).equals(TaskStatus.SUCCESS)) {
                num += Integer.valueOf(entry.getValue());
            }
        } 
        System.out.println("TestBroadcastJob.postProcess(), num=" + num);
        return new ProcessResult(true, String.valueOf(num));
    }

}
