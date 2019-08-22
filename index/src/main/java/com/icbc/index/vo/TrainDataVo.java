package com.icbc.index.vo;

import java.util.ArrayList;
import java.util.List;

public class TrainDataVo {
    private String bankName;
    private List<Double> parse_train_data;
    private List<String> parse_train_time;

    public TrainDataVo(){}
    public TrainDataVo(String bankName, List<Double> parse_train_data, List<String> parse_train_time) {
        this.bankName = bankName;
        this.parse_train_data = parse_train_data;
        this.parse_train_time = parse_train_time;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public List<Double> getParse_train_data() {
        return parse_train_data;
    }

    public void setParse_train_data(List<Double> parse_train_data) {
        this.parse_train_data = parse_train_data;
    }

    public List<String> getParse_train_time() {
        return parse_train_time;
    }

    public void setParse_train_time(List<String> parse_train_time) {
        this.parse_train_time = parse_train_time;
    }
}
