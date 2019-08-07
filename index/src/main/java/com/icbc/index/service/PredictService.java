package com.icbc.index.service;


import com.alibaba.fastjson.JSON;
import com.icbc.index.mapper.PredictDao;
import com.icbc.index.vo.PredictScaleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
