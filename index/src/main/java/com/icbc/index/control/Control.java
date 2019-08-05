package com.icbc.index.control;


import com.alibaba.fastjson.JSONObject;
import com.icbc.index.model.CardData;
import com.icbc.index.model.Manager;
import com.icbc.index.service.*;
import com.icbc.index.util.LoginUtil;
import com.icbc.index.util.VoiceEncodeUtil;
import com.icbc.index.util.VoiceRecognitionUtil;
import com.icbc.index.vo.RequestMessage;
import com.icbc.index.vo.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

/*
 * 控制器，前后台以json数据格式交互
 * @author mychen
 */

@Controller
@EnableScheduling
public class Control {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

}

