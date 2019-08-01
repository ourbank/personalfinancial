package com.icbc.index.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
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
