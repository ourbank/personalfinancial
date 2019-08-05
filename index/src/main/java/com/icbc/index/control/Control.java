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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/*
 * 控制器，前后台以json数据格式交互
 * @author mychen
 */

@Controller
@EnableScheduling
public class Control {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

}

