package com.icbc.index.mapper;


import com.icbc.index.model.CardData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CardDao {

//    @Select("select count(*) as cardNum,bankname as bankName,DATE_FORMAT(createtime,'%Y-%m-%d') as time from card inner join bank on card.bankId = bank.id " +
//            "group by time,bankName having bankName = #{bankname}")
    @Select("select b.num as cardNum, a.bankName as bankName,b.time as time from bank a inner join\n" +
            "    (select bankid,count(id) as num,DATE_FORMAT(createTime,'%Y-%m-%d') as time from card force index (createtime_index)\n" +
            "\t\t\twhere createTime BETWEEN '2009-01-01 00:00:00' and '2019-01-01 00:00:00' and  bankid = (select id from bank where bankname= #{bankname})\n" +
            "\t\t\t\tgroup by time ) as b\n" +
            "        on a.id = b.bankId ")
    List<CardData> getCountByBankName(@Param("bankname") String bankName);


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
