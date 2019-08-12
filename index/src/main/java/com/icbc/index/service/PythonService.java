package com.icbc.index.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icbc.index.dao.CardMountDao;
import com.icbc.index.mapper.CardDao;
import com.icbc.index.model.CardData;
import com.icbc.index.model.CoreInQuerySQL;
import com.icbc.index.model.Inquire;
import com.icbc.index.util.JSONParseUtil;
import com.icbc.index.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class PythonService {

    //模拟http请求，使用在微服务向python请求服务
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CardMountDao cardMountDao;

    @Resource
    CardDao cardDao;

    Logger logger = LoggerFactory.getLogger(PythonService.class);

    public Inquire bulidSQL(String raw) {

        Inquire inquire = new Inquire();
        String cutword = getRawCutWord(raw);
        JSONArray jsonArray = JSON.parseArray(cutword);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            if (object.containsKey("a")) {
                inquire.setOperation(object.getString("a"));
            } else if (object.containsKey("b")) {
                inquire.setTime(object.getString("b"));
            } else if (object.containsKey("c")) {
                inquire.setPlace(object.getString("c"));
            } else if (object.containsKey("d")) {
                inquire.setBusiness(object.getString("d"));
            }

        }

        return inquire;
    }

    public String getRawCutWord(String raw) {

        //请求python微服务
        String cutword = restTemplate.getForEntity("http://127.0.0.1:8000" +
                "/jieba?raw=" + raw, String.class).getBody();
        return cutword;
    }

    public List<CardData> getCardMount() {

        return cardMountDao.getCardMount();
    }

    public String test(String word) {
        String cutWord = getRawCutWord(word);
        JSONArray array = JSONArray.parseArray(cutWord);
        CoreInQuerySQL sql = new CoreInQuerySQL();
        List<String> place = new ArrayList<>();
        String startTime=null;
        String endTime=null;
        String business=null;
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            if (object.containsKey("a")) {

            }
            if (object.containsKey("b")) {
                Date date =new Date();
                String time = (String) object.get("b");
                switch (time){
                    case "去年":
                        int year = TimeUtil.dateToFormatInt(date, "yyyy");
                        startTime= (year-1) +"-01-01";
                        endTime=startTime.replace("-01-01","-12-31");
                        sql.setStrFromDate(startTime);
                        sql.setStrToDate(endTime);
                        break;
                    case "上个月":
                        break;
                    case "前两年":
                        break;
                    case "前两个月":
                        break;
                }
            }
            if (object.containsKey("c")) {
                String temp = (String) object.get("c");
                place.add(temp);
                sql.setCompany(place);
            }
            if (object.containsKey("d")) {
                business = (String) object.get("d");
                sql.setSingleBusiness(business);
            }

        }
        List<CardData> countByBankName = cardDao.getCountByBankName(sql);
        String singleBusJson = JSONParseUtil.getSingleBusJson(startTime, endTime, place, business, countByBankName);
        return singleBusJson;

    }
}
