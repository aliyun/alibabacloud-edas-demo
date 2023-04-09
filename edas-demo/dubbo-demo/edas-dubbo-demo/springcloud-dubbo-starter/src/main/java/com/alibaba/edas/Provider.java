/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.edas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@SpringBootApplication
public class Provider {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Provider.class, args);
    }

    private static final Logger log = LoggerFactory
            .getLogger(Provider.class);

    @Autowired
    private StreamBridge streamBridge;

    @Bean
    public ApplicationRunner producer() {
        final int a  = lookup(10);

        return args -> {
            Map<String, Object> headers = new HashMap<>();
            Message<SimpleMsg> msg = new GenericMessage(
                    new SimpleMsg("Hello RocketMQ For Retrieable ." + a), headers);
            streamBridge.send("producer-out-0", msg);
        };
    }

    private int lookup(int a) {
        return a + Integer.getInteger("def", 9);
    }

    @Bean
    public Consumer<Message<SimpleMsg>> consumer() {
        return msg -> {
            throw new RuntimeException("mock exception.");
        };
    }
}
