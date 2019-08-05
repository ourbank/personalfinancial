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

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public Control(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //用户服务类
    @Autowired
    ManagerService managerService;

    @Autowired
    CardService cardService;

    @Autowired
    PythonService pythonService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RedisService redisService;

    @Autowired
    Hello hello;

    //登录请求
    @RequestMapping(value = "/loginwx", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    String loginwx(@RequestBody Manager manager) {
        boolean temp = managerService.isLegal(manager);
        JSONObject result = new JSONObject();
        if (temp) {
            String code = LoginUtil.getCode(manager);
            String token = LoginUtil.encode(manager);

            manager.setToken(token);
            redisService.setObj(code, manager, 10);

            result.put("result", true);
            result.put("token", token);
            result.put("code", code);

            return result.toJSONString();
        } else {
            result.put("result", false);
            result.put("token", null);
            result.put("code", null);
            return result.toJSONString();
        }


    }

    @RequestMapping(value = "/loginweb", method = RequestMethod.POST)

    public String loginWeb(String code) {

        Manager user = (Manager) redisService.getObj(code);

        if (user == null) {

            return "direct:login.html";
        }

        redisService.del(code);

        return "index.html";
    }

    @RequestMapping(value = "/tologinweb")
    public String toLoginWeb() {

        return "login.html";
    }


    @RequestMapping(value = "/receivevoice", method = RequestMethod.POST)
    public void getResult(@RequestParam("voicefile") MultipartFile voiceFile) {

        VoiceRecognitionUtil.init();
        String temp = VoiceEncodeUtil.getJsonOfVoice(voiceFile);
        String result = JSONObject.parseObject(temp).getString("result");

    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public @ResponseBody
    String test() {

//        String raw="查询广州分行今天开卡数";
//        //String result =pythonService.getRawCutWord();
//        pythonService.bulidSQL(raw);
//        return ;

        return hello.test();

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
    @Scheduled(fixedRate = 100000)
    public void callback() {
        // 发现消息
        //System.out.println("call back");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messagingTemplate.convertAndSend("/topic/callback", "定时推送消息时间: " + df.format(new Date()));
    }

    // 同前端交互,查询数据
    //,produces = "application/json;charset=UTF-8"
    @RequestMapping(value = "/getdata")
    @ResponseBody
    @CrossOrigin
    //@RequestBody String jsonParam
    String testGzData(Model model, HttpServletResponse response, @RequestParam(value = "bankname") String bankName) {
        //Cookie cookie = new Cookie("carddatacookies",cardService.getDaysNumByBankName(bankName));
        //cookie.setPath("/");
        //cookie.setMaxAge(3600);
        //response.addCookie(cookie);
        //response.setHeader("Access-Control-Allow-Origin", "*");
        //JSONObject jb = new JSONObject(jsonParam);
        //String bankName = (String) jb.get("bankname");
        //System.out.println(cardService.getDaysNumByBankName(bankName));
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return cardService.getDaysNumByBankName(bankName);

    }

    @RequestMapping(value = "/t")
    public @ResponseBody
    List<CardData> t() {
        return pythonService.getCardMount();
    }


        /*    @RequestMapping(value = "/receivevoice",method = RequestMethod.POST)
   public String receiceVoice( @RequestParam("voicefile") MultipartFile voiceFile){
        VoiceRecognitionUtil.init();
        String result = VoiceEncodeUtil.getJsonOfVoice(voiceFile);
        JSONObject object =JSONObject.parseObject(result);
        voiceService.cutWord(object.getString("result"));
        System.out.print("原始内容："+result);
        return result;
    }

    @RequestMapping(value = "/cutWord/{str}",method = RequestMethod.GET)
   public String CutWord(@PathVariable(value = "str") String str){
        //String str = "查询昨天广州分行的开卡数";
        WordCutUtil.init();
        String voiceStr = WordCutUtil.cutWord(str);
        System.out.println(voiceStr);
        String result = JSONParseUtil.getMsql(voiceStr);
        return result;
    }*/


}

