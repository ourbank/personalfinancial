package com.icbc.index.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icbc.index.mapper.CardDao;
import com.icbc.index.model.CardData;
import com.icbc.index.model.CoreInQuerySQL;
import com.icbc.index.util.JSONParseUtil;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CardService {

    Logger logger = LoggerFactory.getLogger(CardService.class);
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
        CoreInQuerySQL coreInQuerySQL = new CoreInQuerySQL(startTime,endTime,business,bankNames);
        List<CardData> list = cardDao.getCountByBankName(coreInQuerySQL);
        String out = JSONParseUtil.getSingleBusJson(startTime,endTime,bankNames,business,list);
        logger.info("查询到某行每天的数据："+out);
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

    /**
     * 获取到高频的数据库查询参数，进行数据库查询
     * @return
     */
    public String getRecommendData() {
        //这里先写死,假设推荐的查询是广州分行，深圳分行2018-2019的开卡数
        String staticJson = "{\"business\":\"开卡数\"," +
                "\"starttime\":\"2018-01-01\"," +
                "\"endtime\":\"2019-07-31\"," +
                "\"banknames\":[\"广州分行\",\"深圳分行\"]}";
        JSONObject jb = JSONObject.parseObject(staticJson);
        String out = getDaysNumByBankName(jb);
        return out;
    }
}
