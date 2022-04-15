/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.aliyun.schedulerx.example.api;

import com.alibaba.schedulerx.common.domain.HttpAttribute;
import com.alibaba.schedulerx.common.domain.TimeType;
import com.alibaba.schedulerx.common.util.JsonUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.schedulerx2.model.v20190430.CreateJobRequest;
import com.aliyuncs.schedulerx2.model.v20190430.CreateJobResponse;

/**
 * 创建http任务
 * @author xiaomeng.hxm
 */
public class TestCreateHttpJob {

    public static void main(String[] args) throws Exception {
     // Open API 的接入点，具体查看上表支持地域列表以及购买机器地域填写
        String regionId = "cn-shanghai";
        //鉴权使用的 AccessKeyId，由阿里云官网控制台获取
        String accessKeyId = "xxxxxxx";
        //鉴权使用的 AccessKeySecret，由阿里云官网控制台获取
        String accessKeySecret = "xxxxxxx";
        //产品名称
        String productName ="schedulerx2";
        //对照支持地域列表选择Domain填写
        String domain ="schedulerx..aliyuncs.com";
        //构建 OpenApi 客户端
        DefaultProfile.addEndpoint(regionId, productName, domain);
        DefaultProfile defaultProfile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(defaultProfile);
        
        CreateJobRequest request = new CreateJobRequest();
        request.setNamespace("a0e3ffd7-7962-40f4-9e93-86ca9dc68932");
        request.setGroupId("hxmtest");
        request.setJobType("http");
        request.setName("testHttpJob");
        request.setDescription("测试http任务");
        //cron定时调度
        request.setTimeType(TimeType.CRON.getValue());
        request.setTimeExpression("20 0/5 * * * ?");
        //http任务只支持单机运行
        request.setExecuteMode("standalone");
        HttpAttribute httpAttribute = new HttpAttribute();
        httpAttribute.setUrl("http://127.0.0.1:8080/test");
        httpAttribute.setMethod("POST");
        httpAttribute.setTimeout(10);
        httpAttribute.setRespKey("code");
        httpAttribute.setRespValue("200");
        request.setContent(JsonUtil.toJson(httpAttribute));
        request.setParameters("key1=value1&key2=value2");
        
        //发送请求
        CreateJobResponse response = client.getAcsResponse(request);
        if (!response.getSuccess()) {
            System.out.println(response.getMessage());
            System.out.println("createHttpJob: "+response.getRequestId());
        } else {
            System.out.println(JsonUtil.toJson(response));
        }
    }
    
}
