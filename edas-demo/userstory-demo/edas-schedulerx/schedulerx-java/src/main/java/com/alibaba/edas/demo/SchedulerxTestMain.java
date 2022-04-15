/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.edas.demo;

import com.alibaba.schedulerx.worker.SchedulerxWorker;
import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;

import org.joda.time.DateTime;

public class SchedulerxTestMain extends JavaProcessor {
    public static void main(String[] args) throws Exception {

        initSchedulerxWorker();
        while (true) {
            System.out.println("do some business" + DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            Thread.sleep(1000*10);
        }

    }

    /**
     * 初始化schedulerx客户端
     * */
    private static void initSchedulerxWorker() throws Exception{
        SchedulerxWorker schedulerxWorker = new SchedulerxWorker();
        //只有在本地测试中才会用到，正式线上不需要，无需改代码，本地开发要在启动参数中增加-Dspas.identity
        //endpoint固定是这个
        String endpoint = "acm.aliyun.com";
        //华东1测试环境的命名空间id
        String namespace = "3a6e1a81-dd07-401b-bb8d-b14f573c6fb8";
        schedulerxWorker.setEndpoint(endpoint);
        schedulerxWorker.setNamespace(namespace);
        //以上为本地测试需要

        String groupId = "testSimpleJob.defaultGroup";

        schedulerxWorker.setGroupId(groupId);
        schedulerxWorker.init();
        System.out.println("SchedulerxWorker init success. groupId=" + groupId);
    }

    public ProcessResult process(JobContext jobContext) throws Exception {
        System.out.println("Hello, schedulerx2.0! Java execute scheduler......" + DateTime.now().toString("yyyy-MM-dd  HH:mm:ss"));
        return new ProcessResult(true);
    }
}
