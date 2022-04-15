/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.aliyun.schedulerx.example.api;

import java.util.ArrayList;
import java.util.List;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.schedulerx2.model.v20190430.DesignateWorkersRequest;
import com.aliyuncs.schedulerx2.model.v20190430.DesignateWorkersResponse;
import com.google.gson.Gson;

/**
 * 指定机器或者标签
 * @author xiaomeng.hxm
 */
public class TestDesignateWorkers {

    public static void main(String[] args) {
     // Open API 的接入点，具体查看上表支持地域列表以及购买机器地域填写
        String regionId = "public";
      //鉴权使用的 AccessKeyId，由阿里云官网控制台获取
        String accessKeyId = "xxxxxxxx";
        //鉴权使用的 AccessKeySecret，由阿里云官网控制台获取
        String accessKeySecret = "xxxxxxxxx";
        //产品名称
        String productName ="schedulerx2";
        //对照支持地域列表选择Domain填写
        String domain ="schedulerx.aliyuncs.com";
        //构建 OpenApi 客户端
        DefaultProfile.addEndpoint(regionId, productName, domain);
        DefaultProfile defaultProfile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(defaultProfile);
        
        DesignateWorkersRequest request = new DesignateWorkersRequest();
        request.setNamespace("433d8b23-06e9-408c-aaaa-90d4d1b9a4af");
        request.setGroupId("xuren_test");
        request.setJobId(3048L);
        //是否故障转移
        request.setTransferable(true);
        //1表示指定worker，2表示指定label
        request.setDesignateType(1);
        List<String> workers = new ArrayList<>();
        workers.add("30.225.16.104");
        //workers需要json格式
        request.setWorkers(new Gson().toJson(workers));
        DesignateWorkersResponse response;
        try {
            response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
