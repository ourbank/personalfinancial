package com.icbc.index.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icbc.index.mapper.CardDao;
import com.icbc.index.model.CardData;
import com.icbc.index.model.Msql;
import com.icbc.index.util.JSONParseUtil;
import com.icbc.index.util.SQLProvider;
import com.icbc.index.util.TimeUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CardService {

    @Autowired
    CardDao cardDao;

    @Autowired
    @Qualifier("Q")
    ThreadPoolTaskExecutor myExecutor;

    /**
     * 通过分行的名字获取每一天的开卡数，并拼接成JSON
     * @param json
     * @return
     */
    @SuppressWarnings("unchecked")
    public String getDaysNumByBankName(JSONObject json){
        String business = json.get("business").toString();
        String startTime = json.get("starttime").toString();
        String endTime = json.get("endtime").toString();
        List<String> bankNames = JSONArray.parseArray(json.getJSONArray("banknames").toJSONString(),String.class);
        for (int i = 0; i < bankNames.size() ; i++) {
            if(bankNames.get(i).indexOf("市") != -1)
                bankNames.set(i,bankNames.get(i).substring(0,bankNames.get(i).indexOf("市"))+"分行");
        }
        Msql msql = new Msql(startTime,endTime,business,bankNames);
        List<CardData> list = cardDao.getCountByBankName(msql);
        String out = JSONParseUtil.getSingleBusJson(startTime,endTime,bankNames,business,list);
        System.out.println(out);
        return out;
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
        List<String> list2 = new ArrayList<>();
        list2.add("开卡数");
        list2.add("贷款数目");
        String startTime = "2018-02";
        String endTime = "2019-10";
        ConcurrentHashMap map = new ConcurrentHashMap();
        //启用多线程对不同城市进行查找
//        for (int i = 0; i < list2.size(); i++) {
//            try {
//                map.put(list.get(i),myExecutor.submit(new cardSearchThred(
//                        cardDao,list.get(i).substring(0,list.get(i).indexOf("市"))+"分行",
//                        "开卡数", TimeUtil.praseStartTime(startTime),TimeUtil.praseEndTime(endTime))).get());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
        String out = JSONParseUtil.getJSONMap(startTime,endTime,list,list2,map);
        // 测试哦
        return out;
    }
}
