package com.icbc.personalfinancial.Controller;

import com.icbc.personalfinancial.util.voiceprocess.JSONParseUtil;
import com.icbc.personalfinancial.util.voiceprocess.VoiceEncodeUtil;
import com.icbc.personalfinancial.util.voiceprocess.VoiceRecognitionUtil;
import com.icbc.personalfinancial.util.voiceprocess.WordCutUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class TestController {
    @RequestMapping(value = "/sendData/{data}")
    @ResponseBody
    String testReceice(Model model, @PathVariable(value = "data") String data){
        System.out.println("开始");
        System.out.println(data);
        return "SUCCESS-LIMK";
}

    /**
     * 测试接收音频并解码
     * @param model
     * @param testfile
     * @return
     */
    @RequestMapping(value = "/sendVoice",method = RequestMethod.POST)
    @ResponseBody
    String testReceice(Model model,@RequestParam("testfile") MultipartFile testfile){
        VoiceRecognitionUtil.init();
        String result = VoiceEncodeUtil.getJsonOfVoice(testfile);
        return result;
    }

    /**
     * 测试分词功能
     * @param model
     * @return
     */
    @RequestMapping(value = "/cutWord/{str}")
    @ResponseBody
    String testCutWord(Model model,@PathVariable(value = "str") String str){
        //String str = "查询昨天广州分行的开卡数";
        WordCutUtil.init();
        String voiceStr = WordCutUtil.cutWord(str);
        System.out.println(voiceStr);
        String result = JSONParseUtil.getMsql(voiceStr);
        return result;
    }
}
