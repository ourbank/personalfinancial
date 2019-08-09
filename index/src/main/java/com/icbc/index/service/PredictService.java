package com.icbc.index.service;


import com.alibaba.fastjson.JSON;
import com.icbc.index.mapper.PredictDao;
import com.icbc.index.vo.PredictDataVo;
import com.icbc.index.vo.PredictScaleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PredictService {

    @Autowired
    PredictDao predictDao;

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

    public String getSimplePredictByBus(String business) {
        List<PredictDataVo> out = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        switch(business){
            case "开卡数":
                list.add(6472);
                list.add(7841);
                list.add(8124);
                list.add(9000);
                list.add(10239);
                list.add(11874);
                list.add(12435);
                list.add(13453);
                out.add(new PredictDataVo(business,"广州分行","season",list));
                break;
            case "贷款数":
                list.add(3451);
                list.add(4356);
                list.add(4516);
                list.add(5314);
                list.add(6765);
                list.add(8543);
                list.add(10234);
                list.add(12456);
                out.add(new PredictDataVo(business,"广州分行","season",list));
                break;
            case "存款数":
                list.add(9976);
                list.add(10345);
                list.add(12324);
                list.add(17123);
                list.add(20435);
                list.add(28546);
                list.add(30213);
                list.add(33435);
                out.add(new PredictDataVo(business,"广州分行","season",list));
                break;
            case "中间收入":
                list.add(6472);
                list.add(8543);
                list.add(9976);
                list.add(12324);
                list.add(13453);
                list.add(17123);
                list.add(30213);
                list.add(31278);
                out.add(new PredictDataVo(business,"广州分行","season",list));
                break;
        }
        return JSON.toJSONString(out);
    }
}
