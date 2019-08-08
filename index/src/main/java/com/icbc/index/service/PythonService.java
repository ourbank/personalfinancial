package com.icbc.index.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icbc.index.dao.CardMountDao;
import com.icbc.index.model.CardData;
import com.icbc.index.model.Inquire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class PythonService {

    //模拟http请求，使用在微服务向python请求服务
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CardMountDao cardMountDao;




    public Inquire bulidSQL(String raw){

        Inquire inquire= new Inquire();
        String cutword=getRawCutWord(raw);
        JSONArray jsonArray= JSON.parseArray(cutword);
        for (int i=0;i<jsonArray.size();i++){
            JSONObject object =jsonArray.getJSONObject(i);
            if (object.containsKey("a")){
                inquire.setOperation(object.getString("a"));
            }else if (object.containsKey("b")){
                inquire.setTime(object.getString("b"));
            }else if (object.containsKey("c")){
                inquire.setPlace(object.getString("c"));
            }else if (object.containsKey("d")){
                inquire.setBusiness(object.getString("d"));
            }

        }

        return inquire;
    }

    public String getRawCutWord(String raw) {

        //请求python微服务
        String cutword =restTemplate.getForEntity("http://127.0.0.1:8000" +
                "/jieba?raw="+raw,String.class).getBody();
        return cutword;
    }

    public List<CardData> getCardMount(){

        return cardMountDao.getCardMount();
    }

    public String test(String word){
        String cutWord = getRawCutWord(word);
        System.out.println("分词结果"+cutWord);
        return cutWord;

    }
}
