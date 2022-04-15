/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.aliyun.schedulerx.example.api;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.schedulerx2.model.v20190430.CreateAppGroupRequest;
import com.aliyuncs.schedulerx2.model.v20190430.CreateAppGroupResponse;
import com.google.gson.Gson;

/**
 * 创建应用分组
 * @author xiaomeng.hxm
 */
public class TestCreateAppGroup {

    public static void main(String[] args) throws Exception {
        // Open API 的接入点，具体查看上表支持地域列表以及购买机器地域填写
        String regionId = "cn-huhehaote";
        //鉴权使用的 AccessKeyId，由阿里云官网控制台获取
        String accessKeyId = "xxxxxxx";
        //鉴权使用的 AccessKeySecret，由阿里云官网控制台获取
        String accessKeySecret = "xxxxxxxx";
        //产品名称
        String productName ="schedulerx2";
        //对照支持地域列表选择Domain填写
        String domain ="schedulerx.aliyuncs.com";
        //构建 OpenApi 客户端
        DefaultProfile.addEndpoint(regionId, productName, domain);
        DefaultProfile defaultProfile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(defaultProfile);
        
        CreateAppGroupRequest request = new CreateAppGroupRequest();
        request.setNamespace("5084ff70-0eeb-493b-8d19-492536ff7cb2");
        request.setAppName("xure_app");
        //可选项：可以设置应用的key，作为客户端接入鉴权。不设置appKey的话，会自动生成
        request.setAppKey("123456789");
        request.setDescription("xure_app");
        request.setGroupId("xure_app");
        //是否触发繁忙机器配置
        request.setScheduleBusyWorkers(false);
        
        //发送请求
        CreateAppGroupResponse response = client.getAcsResponse(request);
        if (!response.getSuccess()) {
            System.out.println(response.getMessage());
        } else {
            System.out.println(new Gson().toJson(response));
        }
    }
    
    
}
