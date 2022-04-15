/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.aliyun.schedulerx.example.api;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.schedulerx2.model.v20190430.DisableJobRequest;
import com.aliyuncs.schedulerx2.model.v20190430.DisableJobResponse;

/**
 * 禁用任务
 * @author xiaomeng.hxm
 */
public class TestDisableJob {

    public static void main(String[] args) throws Exception {
        // Open API 的接入点，具体查看上表支持地域列表以及购买机器地域填写
        String regionId = "cn-beijing";
        //鉴权使用的 AccessKeyId，由阿里云官网控制台获取
        String accessKeyId = "xxxxxx";
        //鉴权使用的 AccessKeySecret，由阿里云官网控制台获取
        String accessKeySecret = "xxxxxx";
        String productName ="schedulerx2";
        String domain ="schedulerx.aliyuncs.com";

        //构建OpenAPI客户端。
        DefaultProfile.addEndpoint(regionId, productName, domain);
        DefaultProfile defaultProfile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(defaultProfile);

        DisableJobRequest request = new DisableJobRequest();
        request.setNamespace("a1fd6527-15f9-4b86-9058-820c876251b8");
        request.setGroupId("common-kschedule-console.defaultGroup");
        request.setJobId(5368L);
        DisableJobResponse response = client.getAcsResponse(request);
        if (!response.getSuccess()) {
            System.out.println(response.getMessage());
            System.out.println("DisableJob: "+response.getRequestId());
        } else {
            System.out.println(response.getMessage());
        }
    }
    
}
