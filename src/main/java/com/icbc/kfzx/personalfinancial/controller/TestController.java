package com.icbc.kfzx.personalfinancial.controller;



import com.baidu.aip.speech.AipSpeech;
import com.icbc.kfzx.personalfinancial.util.VoiceEncodeUtil;
import org.apache.commons.io.IOUtils;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.*;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.util.Arrays;

/**
 *  该控制器类用于测试
 */
@Controller
public class TestController {

    /**
     * 测试接收微信小程序发送的字符串
     * @param model
     * @param data
     * @return
     */
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


    @RequestMapping(value = "/toHome")
    String thymeleaf(Model model){
        model.addAttribute("name","limk");
        return "TO_HOME_SUCCESS";
    }
}
