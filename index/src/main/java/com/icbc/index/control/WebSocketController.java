package com.icbc.index.control;

import com.alibaba.fastjson.JSONObject;
import com.icbc.index.configuration.SocketSessionMap;
import com.icbc.index.mapper.CardDao;
import com.icbc.index.model.CardData;
import com.icbc.index.service.PythonInvoke;
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
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 该控制器负责websocket的相关请求和推送
 */
@Controller
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    @Resource
    PythonInvoke pythonInvoke;

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


    public void sendToUser(String token, String message){
        messagingTemplate.convertAndSend("/topic/"+token,message);
    }

    @SubscribeMapping("/topic/recommend")
    @ResponseBody
    public String  sendRecommend(){
        int id = (int) redisService.getObj("id");
        if (id==0)
            id=1;
        MultiValueMap<String,Object> user=new LinkedMultiValueMap<>();
        user.add("user",id);
        String recommend = pythonInvoke.recommend(user);
        return recommend;
    }


}
