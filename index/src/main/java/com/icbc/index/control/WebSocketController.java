package com.icbc.index.control;

import com.icbc.index.configuration.SocketSessionMap;
import com.icbc.index.service.RedisService;
import com.icbc.index.vo.RequestMessage;
import com.icbc.index.vo.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 该控制器负责websocket的相关请求和推送
 */
@Controller
@EnableScheduling
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    SocketSessionMap sessionMap;

    @Autowired
    RedisService redisService;

    @Autowired
    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // 新增websocket功能
    @MessageMapping("/welcome")
    @SendTo("/topic/say")
    public ResponseMessage say(RequestMessage message) {
        System.out.println(message.getName());
        return new ResponseMessage("welcome," + message.getName() + " !");
    }

    /**
     * 定时推送消息
     */
   /* @Scheduled(fixedRate = 100000)
    public void callback() {
        // 发现消息
        //System.out.println("call back");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messagingTemplate.convertAndSend("/topic/callback", "定时推送消息时间: " + df.format(new Date()));
    }
*/


    public void sendToUser(String token,String message){

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messagingTemplate.convertAndSend("/topic/"+token,"定时推送消息时间: "
                + df.format(new Date())+message);
    }
    private MessageHeaders createHeaders(String token) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor
                .create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(token);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }


}
