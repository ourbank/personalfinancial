package com.icbc.index.service;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private  StringRedisTemplate stringRedisTemplate;


    @Resource(name = "stringRedisTemplate")
    private  ValueOperations<String, String> valOpsStr;

    @Autowired
    private  RedisTemplate<Object, Object> redisTemplate;

    @Resource(name = "redisTemplate")
    private  ValueOperations<Object, Object> valOpsObj;

    /**
     * redisTemplate 序列化使用的jdkSerializeable, 存储二进制字节码, 所以自定义序列化类
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // 设置value的序列化规则和 key的序列化规则
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 根据指定key获取String
     * @param key
     * @return
     */
    public  String getStr(String key){

        return valOpsStr.get(key);
    }

    /**
     * 设置Str缓存
     * @param key
     * @param val
     */
    public  void setStr(String key, String val){
        valOpsStr.set(key,val);
    }

    public  void setStr(String key, String value, long expiration){
        valOpsStr.set(key,value,expiration,TimeUnit.MINUTES);
    }

    /**
     * 删除指定key
     * @param key
     */
    public  void del(String key){
        stringRedisTemplate.delete(key);
    }

    /**
     * 根据指定o获取Object
     * @param o
     * @return
     */
    public  Object getObj(Object o){
        return valOpsObj.get(o);
    }

    /**
     * 设置obj缓存
     * @param o1
     * @param o2
     */
    public  void setObj(Object o1, Object o2){
        valOpsObj.set(o1, o2);
    }

    public  void setObj(Object o1, Object o2,long expiration){
        valOpsObj.set(o1, o2,5,TimeUnit.MINUTES);
    }
    /**
     * 删除Obj缓存
     * @param o
     */
    public  void delObj(Object o){
        redisTemplate.delete(o);
    }

    /**
     */
    public  Set<String> getAllkeys(){
        return stringRedisTemplate.keys("*");

    }
}
