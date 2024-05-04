package com.aliware.edas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RedisAccessor implements InitializingBean {
    public static Logger log = LoggerFactory.getLogger(RedisAccessor.class);

    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    private static final String KEY = "requests";

    public void incrementRequests() {
        try {
            redisTemplate.opsForValue().increment(KEY);
        } catch (Exception e) {
            log.warn("redis increment error", e);
        }
    }

    private RedisTemplate createRedisTemplate(){
        RedisTemplate redis = new RedisTemplate();
        redis.setConnectionFactory(redisConnectionFactory);

        RedisSerializer<String> stringSerializer = new StringRedisSerializer();

        redis.setKeySerializer(stringSerializer);

        redis.setValueSerializer(stringSerializer);

        redis.setHashKeySerializer(stringSerializer);

        redis.setEnableDefaultSerializer(true);

        redis.afterPropertiesSet();

        return redis;
    }

    public int getRequests() {
        String val = redisTemplate.opsForValue().get(KEY);
        return val == null ? 0 : Integer.parseInt(val);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        redisTemplate = createRedisTemplate();
    }
}
