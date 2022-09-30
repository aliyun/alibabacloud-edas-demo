/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.aliyun.schedulerx.example.api;

import com.alibaba.schedulerx.common.domain.K8sJobXAttrs;
import com.alibaba.schedulerx.common.domain.TimeType;
import com.alibaba.schedulerx.common.util.JsonUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.schedulerx2.model.v20190430.CreateJobRequest;
import com.aliyuncs.schedulerx2.model.v20190430.CreateJobResponse;
import com.aliyuncs.schedulerx2.model.v20190430.UpdateJobRequest;
import com.aliyuncs.schedulerx2.model.v20190430.UpdateJobResponse;

/**
 * 创建java任务
 * @author xiaomeng.hxm
 */
public class TestUpdateK8sJob {
	
	private static String jobYml = "apiVersion: batch/v1\n"
			+ "kind: Job\n"
			+ "metadata:\n"
			+ "  name: pi\n"
			+ "spec:\n"
			+ "  template:\n"
			+ "    spec:\n"
			+ "      containers:\n"
			+ "      - name: pi\n"
			+ "        image: perl\n"
			+ "        command: [\"perl\",  \"-Mbignum=bpi\", \"-wle\", \"print bpi(10)\"]\n"
			+ "      restartPolicy: Never\n"
			+ "  backoffLimit: 3";

    public static void main(String[] args) throws Exception {
        // Open API 的接入点，具体查看上表支持地域列表以及购买机器地域填写
        String regionId = "cn-huhehaote";
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
        UpdateJobRequest request = new UpdateJobRequest();
        request.setNamespace("5084ff70-0eeb-493b-8d19-492536ff7cb2");
        request.setGroupId("k8s.test");
        request.setJobId(1593L);
        //可以单独更新脚本或者调度时间
        request.setContent(jobYml);
        request.setTimeExpression("0 0 4 * * ?");
        UpdateJobResponse response = client.getAcsResponse(request);
        System.out.println(JsonUtil.toJson(response));
    }
    
}
