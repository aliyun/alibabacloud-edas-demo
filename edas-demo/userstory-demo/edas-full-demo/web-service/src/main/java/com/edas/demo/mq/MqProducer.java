/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.edas.demo.mq;

import com.aliyun.openservices.ons.api.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Properties;
import java.util.UUID;

/**
 * @description
 * @author: kim
 * @create: 2021-08-09 16:36
 **/
@Component
public class MqProducer implements InitializingBean {

    private Producer producer = null;

    @Override
    public void afterPropertiesSet() throws Exception {
        initMqProducer();
    }

    void initMqProducer(){
        Properties properties = new Properties();
        // AccessKey ID阿里云身份验证，在阿里云RAM控制台创建。
        properties.put(PropertyKeyConst.AccessKey,"xxx");
        // AccessKey Secret阿里云身份验证，在阿里云RAM控制台创建。
        properties.put(PropertyKeyConst.SecretKey,"xxx");
        //设置发送超时时间，单位：毫秒。
        properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, "3000");
        // 设置TCP接入域名，进入消息队列RocketMQ版控制台实例详情页面的接入点区域查看。
        properties.put(PropertyKeyConst.NAMESRV_ADDR, "http://your_mq.mq-internet-access.mq-internet.aliyuncs.com:80");

        producer = ONSFactory.createProducer(properties);
        // 在发送消息前，必须调用start方法来启动Producer，只需调用一次即可。
        producer.start();
    }


    boolean syncSendMessage(byte[] data){
        Message msg = new Message(
                // 普通消息所属的Topic，切勿使用普通消息的Topic来收发其他类型的消息。
                "push_mq_scaling_sentinel_demo_test_topic",
                // Message Tag可理解为Gmail中的标签，对消息进行再归类，方便Consumer指定过滤条件在消息队列RocketMQ版的服务器过滤。
                "test",
                // Message Body可以是任何二进制形式的数据，消息队列RocketMQ版不做任何干预。
                // 需要Producer与Consumer协商好一致的序列化和反序列化方式。
                data);
        // 设置代表消息的业务关键属性，请尽可能全局唯一。
        // 以方便您在无法正常收到消息情况下，可通过消息队列RocketMQ版控制台查询消息并补发。
        // 注意：不设置也不会影响消息正常收发。
        msg.setKey("ORDERID_" + UUID.randomUUID());

        try {
            SendResult sendResult = producer.send(msg);
            // 同步发送消息，只要不抛异常就是成功。
            if (sendResult != null) {
                System.out.println(new Date() + " Send mq message success. Topic is:" + msg.getTopic() + " msgId is: " + sendResult.getMessageId());
            }
        }
        catch (Exception e) {
            // 消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理。
            System.out.println(new Date() + " Send mq message failed. Topic is:" + msg.getTopic());
            e.printStackTrace();
        }

        return true;
    }



    boolean asyncSendMessage(byte[] data){
        Message msg = new Message(
                // 普通消息所属的Topic，切勿使用普通消息的Topic来收发其他类型的消息。
                "push_mq_scaling_sentinel_demo_test_topic",
                // Message Tag，可理解为Gmail中的标签，对消息进行再归类，方便Consumer指定过滤条件在消息队列RocketMQ版的服务器过滤。
                "test",
                // Message Body，任何二进制形式的数据，消息队列RocketMQ版不做任何干预，需要Producer与Consumer协商好一致的序列化和反序列化方式。
                data);

        // 设置代表消息的业务关键属性，请尽可能全局唯一。 以方便您在无法正常收到消息情况下，可通过消息队列RocketMQ版控制台查询消息并补发。
        // 注意：不设置也不会影响消息正常收发。
        msg.setKey(UUID.randomUUID().toString());

        // 异步发送消息, 发送结果通过callback返回给客户端。
        producer.sendAsync(msg, new SendCallback() {
            @Override
            public void onSuccess(final SendResult sendResult) {
                // 消息发送成功。
                System.out.println("send message success. topic=" + sendResult.getTopic() + ", msgId=" + sendResult.getMessageId());
            }

            @Override
            public void onException(OnExceptionContext context) {
                // 消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理。
                System.out.println("send message failed. topic=" + context.getTopic() + ", msgId=" + context.getMessageId());
            }
        });

        return true;
    }

}


