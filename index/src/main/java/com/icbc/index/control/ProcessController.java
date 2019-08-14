package com.icbc.index.control;

import com.alibaba.fastjson.JSONObject;
import com.icbc.index.service.CardService;
import com.icbc.index.service.PythonService;
import com.icbc.index.service.VoiceService;
import com.icbc.index.util.VoiceEncodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

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

    @Autowired
    VoiceService voiceService;

    @RequestMapping(value = "/receivevoice", method = RequestMethod.POST, produces = "multipart/form-data")
    public @ResponseBody
    void getResultByWeChat(@RequestParam("voicefile") MultipartFile voiceFile, @RequestParam("token") String token) {
        voiceService.getBaiduToken();
        String temp = VoiceEncodeUtil.getJsonOfVoice(voiceFile);
        logger.info("微信语音转文字结果：" + temp);
        logger.info("开始分词");
        String data = pythonService.getResultByWeChat(temp);
        webSocketController.sendToUser(token, data);
    }

    /**
     * 核心查询业务控制器
     *
     * @param model
     * @param response
     * @param jsonParam
     * @return
     */
    @RequestMapping(value = "/getsinglebuss", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @CrossOrigin
    public String testGzData(Model model, HttpServletResponse response, @RequestBody JSONObject jsonParam) {
        logger.info("前台传递过来查询核心业务" + jsonParam.toJSONString());
        return cardService.getDaysNumByBankName(jsonParam);
    }

    /**
     * 返回推荐的默认查询数据
     *
     * @return json数据
     */
    @RequestMapping(value = "/getdefaultmain", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public String getdefaultmain() {
        return cardService.getRecommendData();
    }

    // 测试用，不用小程序的token
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public @ResponseBody
    void test(@RequestParam("voice") String voice) {
        String test = pythonService.getResultByWeChat(voice);
        webSocketController.sendToUserLimk(test);
    }

}
