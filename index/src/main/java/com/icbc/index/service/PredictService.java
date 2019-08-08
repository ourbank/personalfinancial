package com.icbc.index.service;


import com.alibaba.fastjson.JSON;
import com.icbc.index.mapper.PredictDao;
import com.icbc.index.vo.PredictDataVo;
import com.icbc.index.vo.PredictScaleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PredictService {

    @Autowired
    PredictDao predictDao;

    /**
     * 将拿到的数据封装成json格式
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
}
