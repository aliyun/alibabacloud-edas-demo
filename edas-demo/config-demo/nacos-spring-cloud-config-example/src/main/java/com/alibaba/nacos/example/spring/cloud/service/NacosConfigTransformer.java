package com.alibaba.nacos.example.spring.cloud.service;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class NacosConfigTransformer {
    @Value("${nacos2.username}")
    private String username;

    @Value("${nacos2.ak}")
    private String ak;

    @Value("${nacos2.sk}")
    private String sk;

    @Value("${nacos2.password}")
    private String password;

    @Value("${nacos2.namespace}")
    private String namespace;

    @Value("${nacos2.server}")
    private String endpoint;


    public void publishConfig(String dataId, String groupId, String content) throws NacosException {
        Properties props = new Properties();

        props.put(PropertyKeyConst.USERNAME, username);
        props.put(PropertyKeyConst.PASSWORD, password);
        props.put(PropertyKeyConst.SERVER_ADDR, endpoint);
        props.put(PropertyKeyConst.NAMESPACE, namespace);

        /* Deploy inside EDAS added properties , ---- start ---- */
        props.put(PropertyKeyConst.IS_USE_CLOUD_NAMESPACE_PARSING, "false");

        props.put(PropertyKeyConst.ACCESS_KEY, ak);
        props.put(PropertyKeyConst.SECRET_KEY, sk);
        /* Deploy inside EDAS added properties , ---- end ---- */

        publish(dataId, groupId, content, props);
    }


    private void publish(String dataId, String groupId, String content, Properties props) throws NacosException {
        ConfigService configService = NacosFactory.createConfigService(props);
        configService.publishConfig(dataId, groupId, content);
    }
}
