package com.icbc.index.control;

import com.icbc.index.service.PythonInvoke;
import com.icbc.index.service.VoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

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

    @Resource
    PythonInvoke pythonInvoke;
    private static final String APP_KEY = "z8byo4jFgqSDs1vy2fTOPfhC";
    // 填写网页上申请的APP SECRET 如 $SECRET_KEY="94dc99566550d87f8fa8ece112xxxxx"
    private static final String SECRET_KEY = "xy5g4bcOmaabSu5eHntUTfiRmjoLFMkq";
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public Object test1(){
       return null;

    }

    @RequestMapping(value = "/post",method = RequestMethod.POST)
    public void test2(){

    }

}

