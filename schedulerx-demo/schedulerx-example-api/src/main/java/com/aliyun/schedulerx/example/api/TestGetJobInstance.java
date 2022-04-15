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
import com.aliyuncs.schedulerx2.model.v20190430.GetJobInstanceRequest;
import com.aliyuncs.schedulerx2.model.v20190430.GetJobInstanceResponse;

/**
 * 获取某个任务的任务实例列表
 * @author xiaomeng.hxm
 */
public class TestGetJobInstance {

    public static void main(String[] args) {
        // Open API 的接入点，具体查看上表支持地域列表以及购买机器地域填写
        String regionId = "cn-hangzhou";
        //鉴权使用的 AccessKeyId，由阿里云官网控制台获取
        String accessKeyId = "xxxxxx";
        //鉴权使用的 AccessKeySecret，由阿里云官网控制台获取
        String accessKeySecret = "xxxxxxxxx";
        //产品名称
        String productName = "schedulerx2";
        //对照支持地域列表选择Domain填写
        String domain ="schedulerx.aliyuncs.com";
        //构建 OpenApi 客户端
        DefaultProfile.addEndpoint(regionId, productName, domain);
        DefaultProfile defaultProfile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(defaultProfile);
        
        GetJobInstanceRequest request = new GetJobInstanceRequest();
        request.setNamespace("fd2965c4-4a0a-4ebb-af52-bb62aa4f19f2");
        request.setGroupId("hxm.test");
        request.setJobId(2300691L);
        request.setJobInstanceId(36508287844L);
        
        GetJobInstanceResponse response;
        try {
            response = client.getAcsResponse(request);
            if (!response.getSuccess()) {
                System.out.println(JsonUtil.toJson(response));
                System.out.println(response.getCode());
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
