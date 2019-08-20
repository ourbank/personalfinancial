package com.icbc.index.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icbc.index.mapper.CardDao;
import com.icbc.index.model.CardData;
import com.icbc.index.model.CoreInQuerySQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalysisService {

    @Autowired
    CardDao cardDao;

    /**
     * 获取该段时间的业务总数
     * @param json
     * @return
     */
    @SuppressWarnings("unchecked")
    public Long getDaySum(JSONObject json){
        String business = json.get("business").toString();
        String startTime = json.get("startTime").toString();
        String endTime = json.get("endTime").toString();
        List<String> bankNames = JSONArray.parseArray(json.getJSONArray("banknames").toJSONString(),String.class);
        CoreInQuerySQL coreInQuerySQL = new CoreInQuerySQL(startTime,endTime,business,bankNames);
        List<CardData> list = cardDao.getSumByBankName(coreInQuerySQL);
        long sum = 0;
        for (int i = 0; i <list.size() ; i++) {
            sum += list.get(i).getCardNum();
        }
        return sum;
    }
}
