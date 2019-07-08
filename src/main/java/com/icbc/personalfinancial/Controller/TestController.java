package com.icbc.personalfinancial.Controller;

import com.icbc.personalfinancial.util.VoiceEncodeUtil;
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
        String result = VoiceEncodeUtil.getJsonOfVoice(testfile);
        return result;
    }
}
