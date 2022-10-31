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

/**
 * 创建java任务
 * @author xiaomeng.hxm
 */
public class TestCreateK8sJob {
	
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
			+ "        command: [\"perl\",  \"-Mbignum=bpi\", \"-wle\", \"print bpi(100)\"]\n"
			+ "      restartPolicy: Never\n"
			+ "  backoffLimit: 4";

    public static void main(String[] args) throws Exception {
        // Open API 的接入点，具体查看上表支持地域列表以及购买机器地域填写
        String regionId = "cn-huhehaote";
        //鉴权使用的 AccessKeyId，由阿里云官网控制台获取
        String accessKeyId = "xxxxxx";
        //鉴权使用的 AccessKeySecret，由阿里云官网控制台获取
        String accessKeySecret = "xxxxxx";
        //产品名称
        String productName ="schedulerx2";
        //对照支持地域列表选择Domain填写
        String domain ="schedulerx.aliyuncs.com";
        //构建 OpenApi 客户端
        DefaultProfile.addEndpoint(regionId, productName, domain);
        DefaultProfile defaultProfile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(defaultProfile);
        
        CreateJobRequest request = new CreateJobRequest();
        request.setNamespace("5084ff70-0eeb-493b-8d19-492536ff7cb2");
        request.setGroupId("k8s.test");
        request.setJobType("k8s");
        request.setContent(jobYml);
        K8sJobXAttrs xAttrs = new K8sJobXAttrs();
	xAttrs.setResource("job");
	request.setXAttrs(JsonUtil.toJson(xAttrs));
        //单机运行
        request.setExecuteMode("standalone");
        request.setDescription("test");
        request.setName("k8sjob");
        //timeType=1表示cron, 3表示fixedRate, 4表示secondDelay, 5表示one_time
        request.setTimeType(TimeType.CRON.getValue());
        request.setTimeExpression("0 0 2 * * ?");
        // 监控报警
        request.setTimeoutEnable(true);
        request.setTimeoutKillEnable(true);
        //报警通道设置为default，表示使用应用管理的报警联系人组
        request.setSendChannel("default");
        request.setFailEnable(true);
        request.setTimeout(12300L);
        // 高级配置，配置失败自动重试
        request.setMaxAttempt(0);
        request.setAttemptInterval(30);
        CreateJobResponse response = client.getAcsResponse(request);
        if (response.getSuccess()) {
            System.out.println("jobId=" + response.getData().getJobId());
        } else {
            System.out.println(response.getMessage());
        }
    }
    
}
