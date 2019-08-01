package com.icbc.index.service;

import com.icbc.index.util.WordCutUtil;
import org.springframework.stereotype.Service;

@Service
public class VoiceService {

    public void cutWord(String word){

        WordCutUtil.init();
        String voiceStr = WordCutUtil.cutWord(word);
        System.out.print("查询结果："+voiceStr);
    }
}
