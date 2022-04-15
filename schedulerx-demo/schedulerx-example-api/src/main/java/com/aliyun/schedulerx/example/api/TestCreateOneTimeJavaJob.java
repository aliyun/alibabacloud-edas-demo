/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.aliyun.schedulerx.example.api;

import com.alibaba.schedulerx.common.domain.TimeType;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.schedulerx2.model.v20190430.CreateJobRequest;
import com.aliyuncs.schedulerx2.model.v20190430.CreateJobResponse;

/**
 * 创建java任务
 * @author xiaomeng.hxm
 */
public class TestCreateOneTimeJavaJob {

    public static void main(String[] args) throws Exception {
        // Open API 的接入点，具体查看上表支持地域列表以及购买机器地域填写
        String regionId = "cn-beijing";
        //鉴权使用的 AccessKeyId，由阿里云官网控制台获取
        String accessKeyId = "xxxxxx";
        //鉴权使用的 AccessKeySecret，由阿里云官网控制台获取
        String accessKeySecret = "xxxxxxx";
        //产品名称
        String productName ="schedulerx2";
        //对照支持地域列表选择Domain填写
        String domain ="schedulerx.aliyuncs.com";
        //构建 OpenApi 客户端
        DefaultProfile.addEndpoint(regionId, productName, domain);
        DefaultProfile defaultProfile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(defaultProfile);
        
        CreateJobRequest request = new CreateJobRequest();
        //java任务需要设置ClassName
        request.setJobType("java");
        request.setClassName("com.aliyun.schedulerx.example.processor.SimpleJob");
        //单机运行
        request.setExecuteMode("standalone");
        request.setDescription("test");
        request.setName("SimpleJob");
        //timeType=1表示cron, 3表示fixedRate, 4表示secondDelay, 5表示one_time
        request.setTimeType(TimeType.ONE_TIME.getValue());
        //one_time时间表达式填具体时刻
        request.setTimeExpression("2024-06-02 18:00:00");
        request.setNamespace("433d8b23-06e9-408c-aaaa-90d4d1b9a4af");
        request.setGroupId("xueren_sub");
        // 监控报警
        request.setTimeoutEnable(true);
        request.setTimeoutKillEnable(true);
        //报警通道设置为default，表示使用应用管理的报警联系人组
        request.setSendChannel("default");
        request.setFailEnable(true);
        request.setTimeout(12300L);
        // 高级配置，配置失败自动重试
        request.setMaxAttempt(3);
        request.setAttemptInterval(30);
        CreateJobResponse response = client.getAcsResponse(request);
        if (response.getSuccess()) {
            System.out.println("jobId=" + response.getData().getJobId());
        } else {
            System.out.println(response.getMessage());
        }
    }
    
}
