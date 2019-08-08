package com.icbc.index.entity;

import java.util.Date;

public class Loandetail {

    private Integer id;
    private Integer bankId;
    private Integer loanId;
    private Double tranMoney;
    private Double owe;
    private Date creatTime;

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

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Double getTranMoney() {
        return tranMoney;
    }

    public void setTranMoney(Double tranMoney) {
        this.tranMoney = tranMoney;
    }

    public Double getOwe() {
        return owe;
    }

    public void setOwe(Double owe) {
        this.owe = owe;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }
}
