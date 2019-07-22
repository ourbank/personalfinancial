package com.icbc.personalfinancial.dao;

import com.icbc.personalfinancial.entity.CardData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CardDao {

    @Select("select count(*) as cardNum,bankname as bankName,DATE_FORMAT(createtime,'%Y-%m-%d') as time from card inner join bank on card.bankId = bank.id " +
            "group by time,bankName having bankName = #{bankname}")
    List<CardData> getCountByBankName(@Param("bankname") String bankName);
}
