package com.icbc.index.service;

import com.icbc.index.util.VoiceRecognitionUtil;
import com.icbc.index.util.WordCutUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoiceService {

    @Autowired
    RedisService redisService;

    public void cutWord(String word){
        WordCutUtil.init();
        String voiceStr = WordCutUtil.cutWord(word);
        System.out.print("查询结果："+voiceStr);
    }

    public void getBaiduToken(){
        // 从redis获取是否有百度api token
        if(redisService.getObj("baiduApiToken") != null){
            System.out.println("正在从redis获取baidu token");
            String baiduToken = (String) redisService.getObj("baiduApiToken");
            VoiceRecognitionUtil.init(baiduToken);
        }else {
            System.out.println("正在从官网获取baidu token");
            String baiduToken = VoiceRecognitionUtil.getToken();
            // token持续时间为1个月
            redisService.setObj("baiduApiToken", baiduToken, 30*24*60);
            VoiceRecognitionUtil.init(baiduToken);
        }

    }
}
