package com.icbc.index.mapper;


import com.icbc.index.model.CardData;
import com.icbc.index.model.Msql;
import com.icbc.index.util.SQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface CardDao {


    @SelectProvider(type= SQLProvider.class, method="getSingleBusSql")
    List<CardData> getCountByBankName(Msql msql);


    @Select("SELECT \n" +
            "    a.bankname as bankName,\n" +
            "    b. DAY as time,\n" +
            "    b.num as cardNum\n" +
            "FROM\n" +
            "    bank a\n" +
            "INNER JOIN (\n" +
            "    SELECT\n" +
            "        bankid,\n" +
            "        sum(num) AS num,\n" +
            "        DAY\n" +
            "    FROM\n" +
            "        card_bill\n" +
            "    WHERE\n" +
            "        DAY BETWEEN #{local_start}\n" +
            "    AND #{local_end}\n" +
            "    GROUP BY\n" +
            "        bankid,\n" +
            "        DAY\n" +
            ") b ON a.id = b.bankid\n" +
            "AND a.bankname = #{city}")
    List<CardData> getDataBy3factors(@Param("city") String city, @Param("local_start") String local_start, @Param("local_end") String local_end);
}
