package com.alibaba.schedulerx.example.xxljob.jobhandler;

import org.springframework.stereotype.Component;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.util.ShardingUtil;

/**
 * 兼容 2.1.0 版本通过ShardingUtil获取广播分片
 * @author xiaomeng.hxm
 *
 */
@JobHandler(value="ShardingJobHandler_v2.1")
@Component
public class ShardingJobHandler extends IJobHandler {

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        System.out.println("index:" + ShardingUtil.getShardingVo().getIndex());
        System.out.println("total:" + ShardingUtil.getShardingVo().getTotal());
        return null;
    }

}
