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
import com.aliyuncs.schedulerx2.model.v20190430.ExecuteJobRequest;
import com.aliyuncs.schedulerx2.model.v20190430.ExecuteJobResponse;

/**
 * API运行任务
 * @author xiaomeng.hxm
 */
public class TestExecuteJob {

    public static void main(String[] args) {
        // Open API 的接入点，具体查看上表支持地域列表以及购买机器地域填写
        String regionId = "cn-beijing";
        //鉴权使用的 AccessKeyId，由阿里云官网控制台获取
        String accessKeyId = "xxxxxxxx";
        //鉴权使用的 AccessKeySecret，由阿里云官网控制台获取
        String accessKeySecret = "xxxxxxxxxx";
        //产品名称
        String productName ="schedulerx2";
        //对照支持地域列表选择Domain填写
        String domain ="schedulerx.aliyuncs.com";
        //构建 OpenApi 客户端
        DefaultProfile.addEndpoint(regionId, productName, domain);
        DefaultProfile defaultProfile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(defaultProfile);
        
        ExecuteJobRequest request = new ExecuteJobRequest();
        request.setNamespace("433d8b23-06e9-408c-aaaa-90d4d1b9a4af");
        request.setGroupId("xuren_test");
        request.setJobId(3015L);
        request.setInstanceParameters("{id:123}");
        //可选项：关闭任务状态检查
        //request.setCheckJobStatus(false);
        //可选项：指定机器类型，1：workerAddress 2:label
        //request.setDesignateType(1);
        //可选项：如果指定机器类型是1，设置workerAddr，可以通过GetWorkerList API查询
        //request.setWorker("xxxxxxx@127.0.0.1:222");
        //可选项：如果指定机器类型是2，设置label，可以通过GetWorkerList API查询
        //request.setLabel("gray");
        ExecuteJobResponse response;
        try {
            response = client.getAcsResponse(request);
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
