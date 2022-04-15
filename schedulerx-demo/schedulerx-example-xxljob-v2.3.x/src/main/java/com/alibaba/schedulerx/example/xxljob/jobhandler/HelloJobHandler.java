package com.alibaba.schedulerx.example.xxljob.jobhandler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.stereotype.Component;

/**
 * @author xiaomeng.hxm
 *
 */
@JobHandler(value="HelloJobHandler")
@Component
public class HelloJobHandler extends IJobHandler {

    @Override
    public void execute() throws Exception {
        System.out.println("HelloJobHandler: " + XxlJobHelper.getJobParam());
    }

}
