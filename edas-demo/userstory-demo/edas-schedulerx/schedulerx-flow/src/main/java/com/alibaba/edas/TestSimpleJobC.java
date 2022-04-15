/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.edas;

import java.util.List;

import com.alibaba.schedulerx.common.domain.JobInstanceData;
import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class TestSimpleJobC extends JavaProcessor {
    @Override
    public ProcessResult process(JobContext jobContext) throws Exception {
        System.out.println("come to jobC"+ DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        List<JobInstanceData> upstreamDatas = jobContext.getUpstreamData();
        int sum = 0;
        for (JobInstanceData jobInstanceData : upstreamDatas) {
            System.out.println("jobName=" + jobInstanceData.getJobName()
                + ", data=" + jobInstanceData.getData());
            sum += Integer.valueOf(jobInstanceData.getData());
        }
        System.out.println("TestSimpleJobC sum=" + sum);
        return new ProcessResult(true, String.valueOf(sum));
    }
}
