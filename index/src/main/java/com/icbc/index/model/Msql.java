package com.icbc.index.model;

import java.util.Date;
import java.util.List;

public class Msql {

    // 查询时间范围 2019-01-01
    private Date fromData;
    private Date toData;
    private String strFromDate;
    private String strToDate;
    private String singleBusiness;
    // 子公司名
    private List<String> company;
    // 业务类型： 存款数 贷款数 开户数 中间业务收入
    private List<String> business;
    // 数据库操作类型： 查询 分析 预测
    private String operate;
    // 数据库时间查询间隔
    private String period;

    public Msql() {}

    public Msql(Date fromData, Date toData, List<String> company, List<String> business, String operate, String period) {
        this.fromData = fromData;
        this.toData = toData;
        this.company = company;
        this.business = business;
        this.operate = operate;
        this.period = period;
    }

    public Msql(String strFromDate, String strToDate, String singleBusiness, List<String> company) {
        this.strFromDate = strFromDate;
        this.strToDate = strToDate;
        this.singleBusiness = singleBusiness;
        this.company = company;
    }

    public Date getFromData() {
        return fromData;
    }

    public Date getToData() {
        return toData;
    }

    public String getStrFromDate() {
        return strFromDate;
    }

    public String getStrToDate() {
        return strToDate;
    }

    public String getSingleBusiness() {
        return singleBusiness;
    }

    public List<String> getCompany() {
        return company;
    }

    public List<String> getBusiness() {
        return business;
    }

    public String getOperate() {
        return operate;
    }

    public String getPeriod() {
        return period;
    }
}
