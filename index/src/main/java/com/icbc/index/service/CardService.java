package com.icbc.index.service;


import com.alibaba.fastjson.JSONObject;
import com.icbc.index.mapper.CardDao;
import com.icbc.index.model.CardData;
import com.icbc.index.util.JSONParseUtil;
import com.icbc.index.util.TimeUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class CardService {

    @Autowired
    CardDao cardDao;

    @Autowired
    @Qualifier("Q")
    ThreadPoolTaskExecutor myExecutor;

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
        JSONObject out = new JSONObject(outMap);
        System.out.println(out);
        return out.toJSONString();
    }

    /**
     * 根据前端的json格式请求，调用对应的请求
     * @param jb
     * @return
     */
    public String getDataBy3factors(JSONObject jb) {
//        List<String> list = (List<String>) jb.get("searchcitys");
//        String factor = (String) jb.get("factor");
//        String startTime = (String) jb.get("startV");
//        String endTime = (String) jb.get("endV");
        List<String> list = new ArrayList<>();
        list.add("广州市");
        list.add("深圳市");
        String factor = "开卡数";
        String startTime = "2018-02";
        String endTime = "2019-10";
        ConcurrentHashMap map = new ConcurrentHashMap();
        //启用多线程对不同城市进行查找
        Date time = new Date();
        for (int i = 0; i < list.size(); i++) {
            try {
                map.put(list.get(i),myExecutor.submit(new cardSearchThred(
                        cardDao,list.get(i).substring(0,list.get(i).indexOf("市"))+"分行",
                        factor, TimeUtil.praseStartTime(startTime),TimeUtil.praseEndTime(endTime))).get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println(map.get("广州市"));

        return "";
    }
}
