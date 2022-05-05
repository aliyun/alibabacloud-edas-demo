/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.aliyun.schedulerx.example.processor;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;

/**
 * SimpleHelloProcessor
 * @author yaohui
 */
@Component
public class SimpleHelloProcessor extends JavaProcessor {

    /**
     * 在业务处理过程中，采用logger打印即可完成业务日志信息输出
     */
    private static final Logger logger = LoggerFactory.getLogger("schedulerx");

    @Override
    public ProcessResult process(JobContext jobContext) throws Exception {
        logger.info("It's SimpleHelloProcessor for log4j2 demo.");
        try {
            // 模拟除数为0时出现异常
            logger.info("doing something...");
            TimeUnit.SECONDS.sleep(2L / new Random().nextInt(3));
        }catch (Exception e){
            logger.error("process failed. {}", e.getMessage(), e);
            return new ProcessResult(false, e.getMessage());
        }
        logger.info("SimpleHelloProcessor finished.");
        return new ProcessResult(true);
    }

    @Override
    public void kill(JobContext context) {
    }
}
