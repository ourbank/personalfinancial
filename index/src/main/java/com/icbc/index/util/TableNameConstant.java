package com.icbc.index.util;


/**
 * 四个业务的表名
 */
public class TableNameConstant {
    public static final String CARDNUM = "card_Bill";
    public static final String DEPOSITMONEY = "deposit_bill";
    public static final String LOANMONEY = "loan_bill";
    public static final String INTERMEDIATE_BUSINESS = "intermediate_bill";


    public static String getBusinessTable(String business){
        switch (business){
            case "开卡数":return TableNameConstant.CARDNUM;
            case "存款数":return TableNameConstant.DEPOSITMONEY;
            case "贷款数":return TableNameConstant.LOANMONEY;
            case "中间业务收入":return TableNameConstant.INTERMEDIATE_BUSINESS;
            default:return null;
        }
    }
}
