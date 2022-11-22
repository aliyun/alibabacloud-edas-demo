/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.aliyun.schedulerx.example.api;

import com.alibaba.schedulerx.common.util.JsonUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.schedulerx2.model.v20190430.UpdateJobRequest;
import com.aliyuncs.schedulerx2.model.v20190430.UpdateJobResponse;

/**
 * 更新java任务
 * @author xiaomeng.hxm
 */
public class TestUpdateJavaJob {
	
    public static void main(String[] args) throws Exception {
        // Open API 的接入点，具体查看上表支持地域列表以及购买机器地域填写
        String regionId = "cn-huhehaote";
        //鉴权使用的 AccessKeyId，由阿里云官网控制台获取
        String accessKeyId = "xxxx";
        //鉴权使用的 AccessKeySecret，由阿里云官网控制台获取
        String accessKeySecret = "xxxx";
        //产品名称
        String productName ="schedulerx2";
        //对照支持地域列表选择Domain填写
        String domain ="schedulerx.aliyuncs.com";
        //构建 OpenApi 客户端
        DefaultProfile.addEndpoint(regionId, productName, domain);
        DefaultProfile defaultProfile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(defaultProfile);
        UpdateJobRequest request = new UpdateJobRequest();
        request.setNamespace("5935c3ed-abc2-4c96-85a5-77e809d180fb");
        request.setGroupId("hxm.test");
        request.setJobId(1621L);
        //更新任务时间
//        request.setTimeExpression("0 0 4 * * ?");
        //如果要清空任务参数，可以设置为空字符串
        request.setParameters("");
        //更新任务的描述
        request.setDescription("123");
        UpdateJobResponse response = client.getAcsResponse(request);
        System.out.println(JsonUtil.toJson(response));
    }
    
}
