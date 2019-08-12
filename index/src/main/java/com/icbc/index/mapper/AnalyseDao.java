package com.icbc.index.mapper;

import com.icbc.index.util.SQLProvider;
import com.icbc.index.vo.PredictScaleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface AnalyseDao {
    @SelectProvider(type= SQLProvider.class, method="getScale")
    List<PredictScaleVo> getwordcloudByBus(String period, String buss);
}
