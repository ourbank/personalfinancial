package com.icbc.index.util;

import com.icbc.index.model.Msql;
import org.apache.ibatis.jdbc.SQL;

public class SQLProvider {

    public String getSingleBusSql(Msql msql) {
        //创建SQL对象并设置select语句要查询的列
        SQL innersql = new SQL();
        String startTime = TimeUtil.praseStartTime(msql.getStrFromDate());
        String endTime = TimeUtil.praseEndTime(msql.getStrToDate());
        String bankName;
        innersql.SELECT("bankid", "sum(num) as num", "day");
        innersql.FROM("card_bill");
        innersql.WHERE("DAY BETWEEN '" + startTime + "' and '" + endTime+"'");
        innersql.GROUP_BY("bankid", "day");
        SQL sql = new SQL().SELECT("a.bankname as bankName,b.day as time,b.num as cardNum");
        sql.FROM("bank a"); //添加from语句
        sql.INNER_JOIN("(" + innersql.toString() + ") b on a.id = b.bankid");
        for (int i = 0; i < msql.getCompany().size(); i++) {
            bankName = msql.getCompany().get(i);
            sql.WHERE("a.bankname ='" +bankName+"'");
            if(i != msql.getCompany().size() - 1)
                sql.OR();
        }
        System.out.println("auto sql :"+sql.toString());
        return sql.toString();
    }

}
