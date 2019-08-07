package com.icbc.index.control;



import com.alibaba.fastjson.JSON;
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

    @RequestMapping(value = "/receivevoice", method = RequestMethod.POST,produces = "multipart/form-data")
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
        //return cardService.getDataBy3factors(null);
       // cardService.test();
        return null;
    }

    /**
     *
     * @param model
     * @param response
     * @param jsonParam  请求体
     * @return
     */
    @RequestMapping(value = "/getdata_test",produces = "application/json")
    @ResponseBody
    @CrossOrigin
    //@RequestBody String jsonParam
    String testGzData(Model model, HttpServletResponse response, @RequestBody JSONObject jsonParam) {

        return cardService.getDaysNumByBankName(jsonParam);
    }
}
