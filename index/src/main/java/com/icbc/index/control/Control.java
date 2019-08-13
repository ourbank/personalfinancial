package com.icbc.index.control;

import com.alibaba.fastjson.JSONObject;
import com.icbc.index.service.VoiceService;
import com.icbc.index.util.VoiceEncodeUtil;
import com.icbc.index.util.VoiceRecognitionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/*
 * 控制器，前后台以json数据格式交互
 * @author mychen
 */

@Controller
@EnableScheduling
public class Control {


    @Autowired
    VoiceService voiceService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 测试转码时间用
    @RequestMapping(value = "/testvoice", method = RequestMethod.POST, produces = "multipart/form-data")
    public @ResponseBody
    void getResult() {
        voiceService.getBaiduToken();
        String temp = VoiceEncodeUtil.getJsonOfVoice(null);
//        String result = JSONObject.parseObject(temp).getString("result");
//        logger.info("微信语音转文字结果：" + result);
    }

}

