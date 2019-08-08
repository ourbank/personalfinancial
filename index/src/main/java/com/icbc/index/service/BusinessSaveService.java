package com.icbc.index.service;



import com.icbc.index.mapper.BusinessSaveMapper;
import com.icbc.index.model.BusinessSaveData;
import com.icbc.index.model.Msql;
import com.icbc.index.util.JSONParseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class BusinessSaveService {

    @Resource
    BusinessSaveMapper businessSaveMapper;

    public String getDaysNumByBankName(Map map){
        String startTime = map.get("startTime").toString();
        String endTime = map.get("endTime").toString();
        List<String> cities = (List<String>) map.get("city");
        List<String> business = (List<String>) map.get("business");


        Msql msql = new Msql(startTime,endTime,business,cities);
        List<BusinessSaveData> list = businessSaveMapper.getCountByAddrAndBusiness(msql);

        String out = JSONParseUtil.getSaveJson(startTime,endTime,cities,business,list);
        return out;
    }

}
