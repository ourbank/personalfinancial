package com.icbc.index.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import java.util.List;
import java.util.Map;

/**
 * @description: websocket连接事件监听
 * @author: mychen
 * @create: 2019-08-06 15:22
 **/
@Component
public class STOMPConnectEventListener implements ApplicationListener<SessionConnectEvent> {

    @Autowired
    SocketSessionMap socketSessionMap;

    @Override
    public void onApplicationEvent(SessionConnectEvent sessionConnectedEvent) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(sessionConnectedEvent.getMessage());
        String token = accessor.getNativeHeader("token").get(0);
        String sessionId = accessor.getSessionId();
        socketSessionMap.registerSession(token,sessionId);
    }
}
