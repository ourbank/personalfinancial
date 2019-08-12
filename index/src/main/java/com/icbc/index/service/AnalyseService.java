package com.icbc.index.service;

import com.alibaba.fastjson.JSON;
import com.icbc.index.mapper.AnalyseDao;
import com.icbc.index.vo.PredictScaleVo;
import com.icbc.index.vo.WordCloudVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnalyseService {

    @Resource
    AnalyseDao analyseDao;

    public String getwordcloudByBus(String bus, String period) {
        List<PredictScaleVo> wordcloud =  analyseDao.getwordcloudByBus(period,bus);
        List<WordCloudVo> out = new ArrayList<>();
        for (PredictScaleVo psv : wordcloud){
            out.add(new WordCloudVo(psv.getBankName(),psv.getNum()));
        }
        return JSON.toJSONString(out);
    }
}
