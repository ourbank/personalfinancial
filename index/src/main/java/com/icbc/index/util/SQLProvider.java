package com.icbc.index.util;

import com.icbc.index.model.CoreInQuerySQL;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import java.util.ArrayList;
import java.util.List;

public class SQLProvider {

    Logger logger = LoggerFactory.getLogger(SQLProvider.class);

    public String getSingleBusSql(CoreInQuerySQL coreInQuerySQL) {
        //创建SQL对象并设置select语句要查询的列
        SQL innersql = new SQL();
        String startTime = TimeUtil.praseStartTime(coreInQuerySQL.getStrFromDate());
        String endTime = TimeUtil.praseEndTime(coreInQuerySQL.getStrToDate());
        String bankName;
        innersql.SELECT("bankid", "sum(num) as num", "day");
        innersql.FROM("card_bill");
        innersql.WHERE("DAY BETWEEN '" + startTime + "' and '" + endTime + "'");
        innersql.GROUP_BY("bankid", "day");
        SQL sql = new SQL().SELECT("a.bankname as bankName,b.day as time,b.num as cardNum");
        sql.FROM("bank a"); //添加from语句
        sql.INNER_JOIN("(" + innersql.toString() + ") b on a.id = b.bankid");
        for (int i = 0; i < coreInQuerySQL.getCompany().size(); i++) {
            bankName = coreInQuerySQL.getCompany().get(i);
            sql.WHERE("a.bankname ='" + bankName + "'");
            if (i != coreInQuerySQL.getCompany().size() - 1)
                sql.OR();
        }
        logger.info("核心业务查询:"+sql.toString());
        return sql.toString();
    }


    public String getScale(String period, String buss) {
        SQL innersql = new SQL();
        innersql.SELECT("sum(num) as num,bankid");
        // 判断查询哪张表
        switch (buss) {
            case "开卡数":
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
                    startTime = TimeUtil.dateToFormatInt(now,"yyyy") +"-"+ (mon - 1)+"-01";
                    endTime = TimeUtil.dateToFormatInt(now,"yyyy") +"-"+ (mon - 1)+"-31";
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
        logger.info("根据年月季度查询开卡数："+sql.toString());
        return sql.toString();
    }


    /**
     * 多业务多城市查询sql语句生成
     *例子：SELECT DAY, num
     * FROM Bill
     * WHERE (( business = 'card' OR business = 'deposit' )
     * 	AND ( bankId = 3 OR bankId = 4 )
     * 	AND DAY BETWEEN '2019-01-01' AND '2019-01-10' )
     * ORDER BYDAY
     * @author zhenjin
     * @param coreInQuerySQL
     * @return
     */
    public String CountByAddrAndBusiness(CoreInQuerySQL coreInQuerySQL){
        SQL sql = new SQL();
        String startTime = coreInQuerySQL.getStrFromDate();
        String endTime = coreInQuerySQL.getStrToDate();
        String addr;

        StringBuilder bankCondition = new StringBuilder("(");
        List<Integer> bankId = new ArrayList<>();
        for(int i = 0; i< coreInQuerySQL.getCompany().size(); i++){
            bankId.add(i,BankIdConstant.getBanIdByAddr( coreInQuerySQL.getCompany().get(i)));
            bankCondition.append("bankId  = "+bankId.get(i));
            if(i< coreInQuerySQL.getCompany().size()-1) bankCondition.append(" OR ");
        }
        bankCondition.append(" )");

        StringBuilder businessCondition= new StringBuilder("(");
        List<String> business = new ArrayList<>();
        for(int j = 0; j< coreInQuerySQL.getBusiness().size(); j++){
            business.add(j, coreInQuerySQL.getBusiness().get(j));
            businessCondition.append("business = '" +TableNameConstant.getBillBusiness(business.get(j)) +"'");
            if(j< coreInQuerySQL.getBusiness().size()-1) businessCondition.append(" OR ");
        }
        businessCondition.append(")");

        String timeCondition = "day BETWEEN '" + startTime + "' AND '" + endTime+"'";

        sql.SELECT("day","num");
        sql.FROM("Bill");
        sql.WHERE(businessCondition.toString(), bankCondition.toString() ,timeCondition);
        sql.ORDER_BY("day","business");


        return sql.toString();
    }

}
