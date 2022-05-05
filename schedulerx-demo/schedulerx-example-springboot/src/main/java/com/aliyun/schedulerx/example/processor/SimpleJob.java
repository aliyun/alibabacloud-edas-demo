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
 * 单机任务，所有节点随机选一台执行
 * @author yaohui
 */
@Component
public class SimpleJob extends JavaProcessor {

    /*
     * log4j2/logback配置schedulerxLogAppender，可以进行日志采集
     */
    private static final Logger logger = LoggerFactory.getLogger("schedulerx");

    @Override
    public ProcessResult process(JobContext context) throws Exception {
        System.out.println("this is process, para=" + context.getJobParameters());
        System.out.println("timeExpression=" + context.getTimeExpression());
        logger.info("hello schedulerx!");
        return new ProcessResult(true);
    }
}
