package com.icbc.index.control;

import com.icbc.index.service.VoiceService;
import com.icbc.index.util.ConnUtil;
import com.icbc.index.util.TokenHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/*
 * 控制器，前后台以json数据格式交互
 * @author mychen
 */
@RestController
public class Control {

    @Autowired
    VoiceService service;
    @Autowired
    RestTemplate template;
    private static final String APP_KEY = "z8byo4jFgqSDs1vy2fTOPfhC";
    // 填写网页上申请的APP SECRET 如 $SECRET_KEY="94dc99566550d87f8fa8ece112xxxxx"
    private static final String SECRET_KEY = "xy5g4bcOmaabSu5eHntUTfiRmjoLFMkq";
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public Object test1(){
        service.getBaiduToken();
        return null;
    }

    @RequestMapping(value = "/post",method = RequestMethod.POST)
    public void test2(){

    }

}

