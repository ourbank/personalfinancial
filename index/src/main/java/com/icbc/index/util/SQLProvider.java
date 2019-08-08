package com.icbc.index.util;


import com.icbc.index.model.Msql;
import org.apache.ibatis.jdbc.SQL;

import java.util.ArrayList;
import java.util.List;

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
        innersql.GROUP_BY("bankId", "day");
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

    public String getCountByAddrAndBusiness(Msql msql){
        SQL sql = new SQL();
        String startTime = msql.getStrFromDate();
        String endTime = msql.getStrToDate();
        String addr;

        StringBuilder bankCondition = new StringBuilder("(");
        List<Integer> bankId = new ArrayList<>();
        for(int i = 0; i<msql.getCompany().size();i++){
            bankId.add(BankIdConstant.getBanIdByAddr( msql.getCompany().get(i)),i);
            bankCondition.append("bankId  = "+bankId.get(i));
            if(i<msql.getCompany().size()-1) bankCondition.append(" OR ");
        }
        bankCondition.append(" )");

        StringBuilder businessCondition= new StringBuilder("(");
        List<String> business = new ArrayList<>();
        for(int j = 0;j<msql.getBusiness().size(); j++){
            business.add(j,msql.getBusiness().get(j));
            businessCondition.append("business = " +business.get(j));
            if(j<msql.getBusiness().size()) businessCondition.append(" OR ");
        }
        businessCondition.append(")");

        String timeCondition = "day BETWEEN '" + startTime + "' AND '" + endTime+"'";

        sql.SELECT("select num ");
        sql.FROM("bill");
        sql.WHERE(bankCondition+ " AND " + bankCondition + " AND " +timeCondition);
        sql.ORDER_BY("day");


        return sql.toString();
    }

}
