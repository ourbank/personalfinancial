package com.icbc.index.service;



import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icbc.index.mapper.BusinessSaveMapper;
import com.icbc.index.model.BusinessSaveData;
import com.icbc.index.model.CoreInQuerySQL;
import com.icbc.index.util.BankIdConstant;
import com.icbc.index.util.JSONParseUtil;
import com.icbc.index.util.TableNameConstant;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class BusinessSaveService {

    @Resource
    BusinessSaveMapper businessSaveMapper;

    public JSONObject getDaysNumByBankName(JSONObject objectlist){
        //1.获取JSONobject里面的业务名以及对应的业务值为map
        JSONArray jsonArray = objectlist.getJSONArray("list");

        Iterator iterator = jsonArray.iterator();
        Map<String, List<String>> map = new HashMap<>();
        while (iterator.hasNext()){
            JSONObject jsonObject = (JSONObject)iterator.next();
            //前端传过来的map的key（条件名）
            String keystr = jsonObject.get("panAttrName").toString();
            //前端传过来的map的value（条件值）
            String value = jsonObject.get("panAttrValue").toString();
            //从已经放入的查询条件获取valueList
            List<String> valueList = new ArrayList<>();
            if (map.containsKey(keystr)){
                valueList = map.get(keystr);
                System.out.println("valueList = " + valueList);
                //加入新的value值
                valueList.add(value);
                map.put(keystr,valueList);
            }else {
                valueList.add(value);
                map.put(keystr,valueList);
            }
        }
        for(String key : map.keySet()){
            System.out.print("Key = "+key+"value = "+map.get(key));
        }
        //2.初始化参数
        String startTime = "2019-01-01";
        String endTime = "2019-01-05";
        List<String> banklist = new ArrayList<>();
        List<String> businesslist = new ArrayList<>();
        if (map.containsKey("地点")){
            System.out.println("sql拿到: 地点");
            banklist = map.get("地点");
        }else {
            System.out.println("sql给他一个默认广州的地点");
            banklist.add("广州");
            map.put("地点",banklist);
        }

        if (map.containsKey("业务")){
            System.out.println("sql拿到: 业务");
            businesslist = map.get("业务");
        }else {
            System.out.println("sql给他一个默认业务");
            businesslist.add("存款数");
        }
        if (map.containsKey("日期")){
            System.out.println("sql拿到: 日期");
            startTime = map.get("日期").get(0).substring(2,12);
            endTime = map.get("日期").get(0).substring(15,25);
        }

        //3.查询数据库
        CoreInQuerySQL msql = new CoreInQuerySQL(startTime,endTime,businesslist,banklist);
        List<BusinessSaveData> list = businessSaveMapper.CountDaysByAddrAndBusiness(msql);

        //4.转化为JSON
        TreeSet<Integer> bankSet = new TreeSet<>();
        TreeSet<String> businessSet = new TreeSet<>();
        for (int i=0; i<banklist.size();i++){
            bankSet.add(BankIdConstant.getBanIdByAddr(banklist.get(i)));
        }
        for (int i=0; i<businesslist.size();i++){
            businessSet.add(TableNameConstant.getBillBusiness(businesslist.get(i)));
        }
        JSONObject jsonObject = JSONParseUtil.getSaveJson(bankSet, businessSet, startTime, endTime, list);
//        System.out.println(jsonObject);
        return jsonObject;
    }


}
