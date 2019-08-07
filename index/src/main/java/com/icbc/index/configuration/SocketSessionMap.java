package com.icbc.index.configuration;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
/**
 * @description: session管理类
 * @author: mychen
 * @create: 2019-08-06 15:22
 **/
@Component
public class SocketSessionMap {
    private final static ConcurrentMap<String, String> sessionMap = new ConcurrentHashMap<>();

    /**
     * 注册Session
     * @param token
     * @param sessionId
     */
    public synchronized void registerSession(String token, String sessionId) {
        sessionMap.put(token,sessionId);
    }

    /**
     * 移除Session
     * @param userId
     * @param sessionId
     */
    public synchronized void removeSession(String userId, String sessionId) {
        sessionMap.remove(userId);
    }

    /**
     * 获取用户的SessionId
     * @param token
     * @return
     */
    public String getUserSessionId(String token){
        return sessionMap.get(token);
    }

    /**
     * 获取所有Session集合
     * @return
     */
    public Map<String, String> queryAllSession(){
        return sessionMap;
    }
    /**
     * 获取集合的大小
     */
    public int onlineCount(){
        return sessionMap.size();
    }
}