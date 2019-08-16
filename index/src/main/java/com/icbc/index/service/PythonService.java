package com.icbc.index.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icbc.index.mapper.CardDao;
import com.icbc.index.model.CardData;
import com.icbc.index.model.CoreInQuerySQL;
import com.icbc.index.util.JSONParseUtil;
import com.icbc.index.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class PythonService {


    @Resource
     PythonInvoke pythonInvoke;
    @Resource
    CardDao cardDao;

    Logger logger = LoggerFactory.getLogger(PythonService.class);


    public String getResultByWeChat(String word) {
        String cutWord = pythonInvoke.cutWord(word);
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
                        String current=TimeUtil.dateToMonthStr(date);
                        startTime= current +"-01";
                        endTime=startTime.replace("-01","-31");
                        sql.setStrFromDate(startTime);
                        sql.setStrToDate(endTime);
                        break;
                    case "前两年":
                        break;
                    case "前两个月":
                        default:
                        break;
                }
            }
            if (object.containsKey("c")) {
                String temp = (String) object.get("c");
                if (temp.contains("市")||temp.length()==2){
                    temp=temp.substring(0,2)+"分行";
                }
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
