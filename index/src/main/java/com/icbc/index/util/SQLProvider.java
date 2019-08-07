package com.icbc.index.util;

import com.icbc.index.model.Msql;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

public class SQLProvider {

    public String getSingleBusSql(Msql msql) {
        //创建SQL对象并设置select语句要查询的列
        SQL innersql = new SQL();
        String startTime = TimeUtil.praseStartTime(msql.getStrFromDate());
        String endTime = TimeUtil.praseEndTime(msql.getStrToDate());
        String bankName;
        innersql.SELECT("bankid", "sum(num) as num", "day");
        innersql.FROM("card_bill");
        innersql.WHERE("DAY BETWEEN '" + startTime + "' and '" + endTime + "'");
        innersql.GROUP_BY("bankid", "day");
        SQL sql = new SQL().SELECT("a.bankname as bankName,b.day as time,b.num as cardNum");
        sql.FROM("bank a"); //添加from语句
        sql.INNER_JOIN("(" + innersql.toString() + ") b on a.id = b.bankid");
        for (int i = 0; i < msql.getCompany().size(); i++) {
            bankName = msql.getCompany().get(i);
            sql.WHERE("a.bankname ='" + bankName + "'");
            if (i != msql.getCompany().size() - 1)
                sql.OR();
        }
        System.out.println("auto sql:\n" + sql.toString());
        return sql.toString();
    }


    public String getScale(String period, String buss) {
        SQL innersql = new SQL();
        innersql.SELECT("sum(num) as num,bankid");
        // 判断查询哪张表
        switch (buss) {
            case "card":
                innersql.FROM("card_bill");
                break;
        }
        // 获取查询的起止时间
        String startTime,endTime;
        Date now = new Date();
        switch (period){
            case "year":
                startTime = TimeUtil.dateToFormatInt(now,"yyyy") - 1 +"-01-01";
                endTime = TimeUtil.dateToFormatInt(now,"yyyy") - 1 +"-12-31";
                innersql.WHERE("day between '"+startTime+"' and '"+endTime+"'");
                break;
            case "season":
                if(TimeUtil.dateToFormatInt(now,"MM") - 3 > 0){
                    int mon = TimeUtil.dateToFormatInt(now,"MM");
                    startTime = TimeUtil.dateToFormatInt(now,"yyyy")+"-"+((( mon - 1 )/3 - 1 )* 3 + 1 )+"-01";
                    endTime = TimeUtil.dateToFormatInt(now,"yyyy")+"-"+((( mon - 1 ) /3 - 1 )  * 3 + 3 )+"-31";
                }else {
                    startTime = TimeUtil.dateToFormatInt(now,"yyyy") - 1 +"-10-01";
                    endTime = TimeUtil.dateToFormatInt(now,"yyyy") - 1 +"-12-31";
                }
                innersql.WHERE("day between '"+startTime+"' and '"+endTime+"'");
                break;
            case "month":
                if(TimeUtil.dateToFormatInt(now,"MM") - 1 > 0){
                    int mon = TimeUtil.dateToFormatInt(now,"MM");
                    startTime = TimeUtil.dateToFormatInt(now,"yyyy") +(mon - 1)+"-01";
                    endTime = TimeUtil.dateToFormatInt(now,"yyyy") +(mon - 1)+"-31";
                }else{
                    startTime = TimeUtil.dateToFormatInt(now,"yyyy") -1 +"12-01";
                    endTime = TimeUtil.dateToFormatInt(now,"yyyy") - 1 +"12-31";
                }
                innersql.WHERE("day between '"+startTime+"' and '"+endTime+"'");
                break;
        }
        innersql.GROUP_BY("bankid");
        SQL sql = new SQL();
        sql.SELECT("b.num as num, a.bankname as bankName");
        sql.FROM("bank a");
        sql.INNER_JOIN("("+innersql.toString()+") b on a.id = b.bankid");
        System.out.println("auto sql:\n" + sql.toString());
        return sql.toString();
    }

}
