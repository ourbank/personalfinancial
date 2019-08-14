package com.icbc.index.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.icbc.index.mapper.CardDao;
import com.icbc.index.mapper.PredictDao;
import com.icbc.index.model.CardData;
import com.icbc.index.model.CoreInQuerySQL;
import com.icbc.index.util.TimeUtil;
import com.icbc.index.vo.PredictDataVo;
import com.icbc.index.vo.PredictScaleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;

@Service
public class PredictService {

    @Resource
    PredictDao predictDao;

    @Resource
    CardDao cardDao;

    @Autowired
    RestTemplate restTemplate;
    /**
     * 将拿到的数据封装成json格式，暂时使用测试数据
     * @param period
     * @param buss
     * @return
     */
    public String getScaleByPeriod(String period, String buss) {
        List<PredictScaleVo> scale =  predictDao.getScaleByPeriod(period,buss);
        return JSON.toJSONString(scale);
    }

    public String getdefaultpredict() {
        // 这里先写测试数据
        List<PredictDataVo> out = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        list.add(7888);
        list.add(8000);
        list.add(8031);
        list.add(9000);
        list.add(9333);
        list.add(9987);
        list.add(10231);
        list.add(10553);
        out.add(new PredictDataVo("开卡数","广州分行","season",list));
        return JSON.toJSONString(out);
    }

    /**
     * 通过业务进行预测，默认时间为6个月
     * @param business
     * @return
     */
    public String getSimplePredictByBus(String business) {
        List<PredictDataVo> out = new ArrayList<>();
        // 获取训练数据
        System.out.println("------获取训练数据------");
        List<String> bankNames = new ArrayList<>();
        bankNames.add("广州分行");
        CoreInQuerySQL msql = new CoreInQuerySQL("2009-01-01","2019-12-31",business,bankNames);
        List<CardData> train_data = cardDao.getCountByBankName(msql);
        List<Integer> parse_train_data = new ArrayList<>();
        List<String> parse_train_time = new ArrayList<>();
        for (int i = 0; i < train_data.size(); i++) {
            parse_train_data.add(train_data.get(i).getCardNum());
            parse_train_time.add(TimeUtil.dateToFormatStr(train_data.get(i).getTime(),"yyyy-MM-dd"));
        }
        JSONArray json_train_data = JSONArray.parseArray(JSON.toJSONString(parse_train_data));
        JSONArray json_train_time = JSONArray.parseArray(JSON.toJSONString(parse_train_time));
        System.out.println(json_train_time);
        System.out.println("------开始与python服务连接------");
        String url = "http://127.0.0.1:8000/predict/";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> params = new HashMap<>();
        params.put("traindata", json_train_data);
        params.put("traintime", json_train_time);
        params.put("business", "开卡数");
        params.put("unit", "month");
        params.put("period", 6);
        HttpEntity httpEntity = new HttpEntity(params,headers);
        ResponseEntity<String> request = restTemplate.postForEntity(url, httpEntity,String.class);
        System.out.println(request.getBody());
//        List<Integer> list = new ArrayList<>();
//        switch(business){
//            case "开卡数":
//                list.add(6472);
//                list.add(7841);
//                list.add(8124);
//                list.add(9000);
//                list.add(10239);
//                list.add(11874);
//                list.add(12435);
//                list.add(13453);
//                out.add(new PredictDataVo(business,"广州分行","season",list));
//                break;
//            case "贷款数":
//                list.add(3451);
//                list.add(4356);
//                list.add(4516);
//                list.add(5314);
//                list.add(6765);
//                list.add(8543);
//                list.add(10234);
//                list.add(12456);
//                out.add(new PredictDataVo(business,"广州分行","season",list));
//                break;
//            case "存款数":
//                list.add(9976);
//                list.add(10345);
//                list.add(12324);
//                list.add(17123);
//                list.add(20435);
//                list.add(28546);
//                list.add(30213);
//                list.add(33435);
//                out.add(new PredictDataVo(business,"广州分行","season",list));
//                break;
//            case "中间收入":
//                list.add(6472);
//                list.add(8543);
//                list.add(9976);
//                list.add(12324);
//                list.add(13453);
//                list.add(17123);
//                list.add(30213);
//                list.add(31278);
//                out.add(new PredictDataVo(business,"广州分行","season",list));
//                break;
//        }
        return JSON.toJSONString(out);
    }
}
