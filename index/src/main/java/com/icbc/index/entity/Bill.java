package com.icbc.index.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Bill {
    private Integer id;
    private Integer bankId;
    private Date day;
    private BigDecimal num;
    private Enum business;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public BigDecimal getNum() {
        return num;
    }

    public void setNum(BigDecimal num) {
        this.num = num;
    }

    public Enum getBusiness() {
        return business;
    }

    public void setBusiness(Enum business) {
        this.business = business;
    }
}
