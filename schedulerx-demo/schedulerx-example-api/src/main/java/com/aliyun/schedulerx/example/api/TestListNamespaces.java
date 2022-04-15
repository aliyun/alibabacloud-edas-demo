/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.aliyun.schedulerx.example.api;

import java.util.List;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.schedulerx2.model.v20190430.ListNamespacesRequest;
import com.aliyuncs.schedulerx2.model.v20190430.ListNamespacesResponse;
import com.aliyuncs.schedulerx2.model.v20190430.ListNamespacesResponse.Data.Namespace;

/**
 * 获取某个账户下所有的命名空间列表
 * @author xiaomeng.hxm
 */
public class TestListNamespaces {

    public static void main(String[] args) {
     // Open API 的接入点，具体查看上表支持地域列表以及购买机器地域填写
        String regionId = "cn-beijing";
        //鉴权使用的 AccessKeyId，由阿里云官网控制台获取
        String accessKeyId = "xxxxxx";
        //鉴权使用的 AccessKeySecret，由阿里云官网控制台获取
        String accessKeySecret = "xxxxx";
        //产品名称
        String productName ="schedulerx2";
        //对照支持地域列表选择Domain填写
        String domain ="schedulerx.aliyuncs.com";
        //构建 OpenApi 客户端
        DefaultProfile.addEndpoint(regionId, productName, domain);
        DefaultProfile defaultProfile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(defaultProfile);
        
        ListNamespacesRequest request = new ListNamespacesRequest();
        ListNamespacesResponse response;
        try {
            response = client.getAcsResponse(request);
            if (!response.getSuccess()) {
                System.out.println(response.getMessage());
            } else {
                List<Namespace> namespaces = response.getData().getNamespaces();
                for (Namespace namespace : namespaces) {
                    System.out.println("namespace uid=" + namespace.getUId());
                }
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
