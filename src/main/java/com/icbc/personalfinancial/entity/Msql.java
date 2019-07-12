package com.icbc.personalfinancial.entity;

import java.util.Date;
import java.util.List;

public class Msql {

    // 查询时间范围 2019-01-01
    private Date fromData;
    private Date toData;
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

    public Date getFromData() {
        return fromData;
    }

    public void setFromData(Date fromData) {
        this.fromData = fromData;
    }

    public Date getToData() {
        return toData;
    }

    public void setToData(Date toData) {
        this.toData = toData;
    }

    public List<String> getCompany() {
        return company;
    }

    public void setCompany(List<String> company) {
        this.company = company;
    }

    public List<String> getBusiness() {
        return business;
    }

    public void setBusiness(List<String> business) {
        this.business = business;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
