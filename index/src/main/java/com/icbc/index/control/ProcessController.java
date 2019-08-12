package com.icbc.index.control;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.icbc.index.service.CardService;
import com.icbc.index.service.PythonService;
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

    @Autowired
    WebSocketController webSocketController;

    @Autowired
    PythonService pythonService;

    @RequestMapping(value = "/receivevoice", method = RequestMethod.POST,produces = "multipart/form-data")
    public @ResponseBody void getResult(@RequestParam("voicefile") MultipartFile voiceFile,@RequestParam("token") String token) {
        VoiceRecognitionUtil.init();
        String temp = VoiceEncodeUtil.getJsonOfVoice(voiceFile);
        String result = JSONObject.parseObject(temp).getString("result");
        pythonService.test(result);

        webSocketController.sendToUser(token,result);
    }

    /**
     * 核心查询业务控制器
     * @param model
     * @param response
     * @param jsonParam
     * @return
     */
    @RequestMapping(value = "/getsinglebuss", method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    @CrossOrigin
    String testGzData(Model model, HttpServletResponse response, @RequestBody JSONObject jsonParam) {
        return cardService.getDaysNumByBankName(jsonParam);
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public @ResponseBody void test(@RequestParam("voice") String voice,@RequestParam("token") String token) {
        String test = pythonService.test(voice);
        webSocketController.sendToUser(token,test);
    }

}
