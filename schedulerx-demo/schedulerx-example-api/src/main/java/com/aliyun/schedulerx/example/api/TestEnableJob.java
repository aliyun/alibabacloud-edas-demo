/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.aliyun.schedulerx.example.api;

import com.alibaba.schedulerx.common.util.JsonUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.schedulerx2.model.v20190430.EnableJobRequest;
import com.aliyuncs.schedulerx2.model.v20190430.EnableJobResponse;

/**
 * 启用任务
 * @author xiaomeng.hxm
 */
public class TestEnableJob {

    public static void main(String[] args) {
        // Open API 的接入点，具体查看上表支持地域列表以及购买机器地域填写
        String regionId = "cn-beijing";
        //鉴权使用的 AccessKeyId，由阿里云官网控制台获取
        String accessKeyId = "xxxxxx";
        //鉴权使用的 AccessKeySecret，由阿里云官网控制台获取
        String accessKeySecret = "xxxxxx";
        String productName ="schedulerx2";
        String domain ="schedulerx.aliyuncs.com";
        //构建 OpenApi 客户端
        DefaultProfile.addEndpoint(regionId, productName, domain);
        DefaultProfile defaultProfile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(defaultProfile);
        
        EnableJobRequest request = new EnableJobRequest();
        request.setNamespace("433d8b23-06e9-408c-aaaa-90d4d1b9a4af");
        request.setGroupId("xueren_sub");
        request.setJobId(6613L);
        EnableJobResponse response;
        try {
            response = client.getAcsResponse(request);
            System.out.println(response.getRequestId());
            if (!response.getSuccess()) {
                System.out.println(JsonUtil.toJson(response));
            } else {
                
                System.out.println(JsonUtil.toJson(response));
            }
        } catch (ServerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
