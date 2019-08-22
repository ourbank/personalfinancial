package com.icbc.index.model;

import java.util.Date;
import java.util.List;

public class CoreInQuerySQL {

    // 查询时间范围 2019-01-01
    private Date fromDate;
    private Date toDate;
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

    public CoreInQuerySQL() {}

    public CoreInQuerySQL(Date fromData, Date toData, List<String> company, List<String> business, String operate, String period) {
        this.fromDate = fromData;
        this.toDate = toData;
        this.company = company;
        this.business = business;
        this.operate = operate;
        this.period = period;
    }

    public CoreInQuerySQL(String strFromDate, String strToDate, String singleBusiness, List<String> company) {
        this.strFromDate = strFromDate;
        this.strToDate = strToDate;
        this.singleBusiness = singleBusiness;
        this.company = company;
    }

    /**
     * 多业务多城市查询保存
     * @param strFromDate
     * @param strToDate
     * @param business
     * @param company
     */
    public CoreInQuerySQL(String strFromDate, String strToDate, List<String> business, List<String> company) {
        this.strFromDate = strFromDate;
        this.strToDate = strToDate;
        this.business = business;
        this.company = company;
    }

    /**
     * 分析查询
     * @param strFromDate
     * @param strToDate
     * @param singleBusiness
     */
    public CoreInQuerySQL(String strFromDate, String strToDate,String singleBusiness) {
        this.strFromDate = strFromDate;
        this.strToDate = strToDate;
        this.singleBusiness=singleBusiness;
    }

    public Date getFromData() {
        return fromDate;
    }

    public Date getToData() {
        return toDate;
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

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public void setStrFromDate(String strFromDate) {
        this.strFromDate = strFromDate;
    }

    public void setStrToDate(String strToDate) {
        this.strToDate = strToDate;
    }

    public void setSingleBusiness(String singleBusiness) {
        this.singleBusiness = singleBusiness;
    }

    public void setCompany(List<String> company) {
        this.company = company;
    }

    public void setBusiness(List<String> business) {
        this.business = business;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
