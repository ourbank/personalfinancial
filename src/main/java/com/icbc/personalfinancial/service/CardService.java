package com.icbc.personalfinancial.service;

import com.icbc.personalfinancial.dao.CardDao;
import com.icbc.personalfinancial.util.voiceprocess.JSONParseUtil;
import com.icbc.personalfinancial.util.voiceprocess.TimeUtil;
import com.icbc.personalfinancial.entity.CardData;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CardService {

    @Autowired
    CardDao cardDao;

    /**
     * 通过分行的名字获取每一天的开卡数，并拼接成JSON
     * @param bankName
     * @return
     */
    @SuppressWarnings("unchecked")
    public String getDaysNumByBankName(String bankName){
        List<CardData> list = cardDao.getCountByBankName(bankName);
        // 获取起止年
        int startYear = 2100;
        int endYear = 0;
        for(CardData s : list){
            if(Integer.parseInt(TimeUtil.dateToYearStr(s.getTime())) > endYear){
                endYear = Integer.parseInt(TimeUtil.dateToYearStr(s.getTime()));
            }
            if(Integer.parseInt(TimeUtil.dateToYearStr(s.getTime())) < startYear){
                startYear = Integer.parseInt(TimeUtil.dateToYearStr(s.getTime()));
            }
        }
        // 获取一个全0的Map结构体
        Map outMap = JSONParseUtil.getDefaultMap(startYear,endYear,bankName,"card");
        // 修改JSON结构体中的数据
        int year, month,day;
        for(int i = 0; i < list.size() ;i++){
            CardData s = list.get(i);
            year = Integer.parseInt(TimeUtil.dateToFormatStr(s.getTime(),"yyyy"));
            month = Integer.parseInt(TimeUtil.dateToFormatStr(s.getTime(),"MM"));
            day = Integer.parseInt(TimeUtil.dateToFormatStr(s.getTime(),"dd"));
            ((ArrayList<Integer>)
                    ((ArrayList<Map>)
                            ((ArrayList<Map>)outMap.get("data")).get(year-startYear)
                    .get("data")).get(month-1)
             .get("data")).set(day - 1,s.getCardNum());
        }
        String out = JSONObject.valueToString(outMap);
        System.out.println(out);
        return out;
    }
}
