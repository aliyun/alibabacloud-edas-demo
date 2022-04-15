/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.edas.service.mq;

import com.aliyun.openservices.ons.api.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class MqConsumer implements InitializingBean {

    private Consumer consumer;

    @Override
    public void afterPropertiesSet() throws Exception {
        Properties properties = new Properties();
        // 您在消息队列RocketMQ版控制台创建的Group ID。
        properties.put(PropertyKeyConst.GROUP_ID,"GID_push_demo_test");
        // AccessKey ID阿里云身份验证，在阿里云RAM控制台创建。
        properties.put(PropertyKeyConst.AccessKey,"xxx");
        // AccessKey Secret阿里云身份验证，在阿里云RAM控制台创建。
        properties.put(PropertyKeyConst.SecretKey,"xxx");
        // 设置TCP接入域名，进入消息队列RocketMQ版控制台实例详情页面的接入点区域查看。
        properties.put(PropertyKeyConst.NAMESRV_ADDR, "http://your_mq.mq-internet-access.mq-internet.aliyuncs.com:80");        // 顺序消息消费失败进行重试前的等待时间，单位（毫秒），取值范围: 10毫秒~30,000毫秒。
        properties.put(PropertyKeyConst.SuspendTimeMillis,"100");
        // 消息消费失败时的最大重试次数。
        properties.put(PropertyKeyConst.MaxReconsumeTimes,"20");

        // 在订阅消息前，必须调用start方法来启动Consumer，只需调用一次即可。
        consumer = ONSFactory.createConsumer(properties);


        consumer.subscribe(
                // Message所属的Topic。
                "push_mq_scaling_sentinel_demo_test_topic",
                // 订阅指定Topic下的Tags：
                // 1. * 表示订阅所有消息。
                // 2. TagA || TagB || TagC表示订阅TagA或TagB或TagC的消息。
                "*",
                new MessageListener() {
                    @Override
                    public Action consume(Message message, ConsumeContext context) {
                        System.out.println(message);
                        return Action.CommitMessage;
                    }
                });

        consumer.start();
    }


}
