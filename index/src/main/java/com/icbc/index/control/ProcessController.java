package com.icbc.index.control;



import com.alibaba.fastjson.JSONObject;
import com.icbc.index.service.CardService;
import com.icbc.index.util.VoiceEncodeUtil;
import com.icbc.index.util.VoiceRecognitionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.Soundbank;
import java.util.List;

/**
 * 该控制器负责处理前端ajax数据请求
 */
@Controller
public class ProcessController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CardService cardService;

    @RequestMapping(value = "/receivevoice", method = RequestMethod.POST)
    public @ResponseBody String getResult(@RequestParam("voicefile") MultipartFile voiceFile,@RequestParam("token") String token) {
        VoiceRecognitionUtil.init();
        String temp = VoiceEncodeUtil.getJsonOfVoice(voiceFile);
        String result = JSONObject.parseObject(temp).getString("result");
        System.out.println(result+token);
        return result;

    }

    @RequestMapping(value = "/getdata",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    @CrossOrigin
        //@RequestBody String json
    String getDataBy3factors() {
        //JSONObject jb = JSONObject.parseObject(json);
        return cardService.getDataBy3factors(null);
    }

    /**
     * 根据银行的名字，返回10年的开卡数据，主要用于前期测试
     * @param model
     * @param response
     * @param bankName  银行姓名
     * @return
     */
    @RequestMapping(value = "/getdata_test")
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
