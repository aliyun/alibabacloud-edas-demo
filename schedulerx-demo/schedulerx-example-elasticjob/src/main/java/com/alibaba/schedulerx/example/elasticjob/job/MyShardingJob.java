/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.schedulerx.example.elasticjob.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.schedulerx.example.elasticjob.service.HelloService;

/**
* 
* @author xiaomeng.hxm
*/
@Component
public class MyShardingJob implements SimpleJob {
    private static final Logger LOGGER = LogManager.getLogger("schedulerx");

    @Autowired
    private HelloService helloService;

    @Override
    public void execute(ShardingContext context) {
        LOGGER.info("jobName:" + context.getJobName() + ", shardingItem=" + context.getShardingItem()
        + ", shardingTotal=" + context.getShardingTotalCount() + ", taskId=" + context.getTaskId());
    }

}
