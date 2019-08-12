package com.icbc.index.mapper;


import com.icbc.index.model.BusinessSaveData;
import com.icbc.index.model.CoreInQuerySQL;
import com.icbc.index.util.SQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface BusinessSaveMapper {

    @SelectProvider(type = SQLProvider.class, method = "CountByAddrAndBusiness")
    List<BusinessSaveData> CountDaysByAddrAndBusiness(CoreInQuerySQL coreInQuerySQL);
   }
