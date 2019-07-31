package com.icbc.personalfinancial.controller;

import com.icbc.personalfinancial.service.CardService;
import com.icbc.personalfinancial.util.voiceprocess.JSONParseUtil;
import com.icbc.personalfinancial.util.voiceprocess.VoiceEncodeUtil;
import com.icbc.personalfinancial.util.voiceprocess.VoiceRecognitionUtil;
import com.icbc.personalfinancial.util.voiceprocess.WordCutUtil;
import com.icbc.personalfinancial.vo.RequestMessage;
import com.icbc.personalfinancial.vo.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@EnableScheduling
public class TestController {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public TestController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Autowired
    CardService cardService;

    @RequestMapping(value = "/sendData/{data}")
    @ResponseBody
    String testReceice(Model model, @PathVariable(value = "data") String data) {
        System.out.println("开始");
        System.out.println(data);
        return "SUCCESS-LIMK";
    }

    @RequestMapping(value = "/to")
    //@ResponseBody
    String heiheihei() {
        return "login.html";
    }

    /**
     * 测试接收音频并解码
     *
     * @param model
     * @param testfile
     * @return
     */
    @RequestMapping(value = "/sendVoice", method = RequestMethod.POST)
    @ResponseBody
    String testReceice(Model model, @RequestParam("testfile") MultipartFile testfile) {
        VoiceRecognitionUtil.init();
        String result = VoiceEncodeUtil.getJsonOfVoice(testfile);
        return result;
    }

    /**
     * 测试分词功能
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/cutWord/{str}")
    @ResponseBody
    String testCutWord(Model model, @PathVariable(value = "str") String str) {
        //String str = "查询昨天广州分行的开卡数";
        WordCutUtil.init();
        String voiceStr = WordCutUtil.cutWord(str);
        System.out.println(voiceStr);
        String result = JSONParseUtil.getMsql(voiceStr);
        return result;
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
    @Scheduled(fixedRate = 10000)
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
}
