package com.icbc.index.util;


/**
 * 四个业务的表名和业务名,用来生成sql
 * @author zhenjin
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

    public static String getBillBusiness(String business){
        switch (business){
            case "开卡数":return "card";
            case "存款数":return "deposit";
            case "贷款数":return "loan";
            case "中间业务收入":return "intermediate";
            default:return null;
        }
    }

    public static String getBusinessNameByBill(String business){
        switch (business){
            case "card": return "开卡数";
            case "deposit":return "存款数";
            case "loan":return "贷款数";
            case "intermediate":return "中间业务收入";
            default:return null;
        }
    }

}
