package com.icbc.index.vo;

import java.util.List;

public class PredictDataVo {
    private String business;
    private String bankName;
    private String predictUnit;
    private List<Integer> predictData;

    public PredictDataVo() {
    }

    public PredictDataVo(String business, String bankName, String predictUnit, List<Integer> predictData) {
        this.business = business;
        this.bankName = bankName;
        this.predictUnit = predictUnit;
        this.predictData = predictData;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getPredictUnit() {
        return predictUnit;
    }

    public void setPredictUnit(String predictUnit) {
        this.predictUnit = predictUnit;
    }

    public List<Integer> getPredictData() {
        return predictData;
    }

    public void setPredictData(List<Integer> predictData) {
        this.predictData = predictData;
    }
}
