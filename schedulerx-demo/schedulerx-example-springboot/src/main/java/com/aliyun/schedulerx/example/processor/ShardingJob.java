/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.aliyun.schedulerx.example.processor;

import org.springframework.stereotype.Component;

import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;

/**
 * 分片任务，分布式模型需要选择sharding
 * @author xiaomeng.hxm
 */
@Component
public class ShardingJob extends JavaProcessor {

    @Override
    public ProcessResult process(JobContext context) throws Exception {
        System.out.println("分片id=" + context.getShardingId() + ", 分片参数=" + context.getShardingParameter());
        return new ProcessResult(true);
    }

}
