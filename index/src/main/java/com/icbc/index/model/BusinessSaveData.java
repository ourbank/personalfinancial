package com.icbc.index.model;

import java.math.BigDecimal;

public class BusinessSaveData {

    private String day;
    private BigDecimal num;
    private String tableCol;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public BigDecimal getNum() {
        return num;
    }

    public void setNum(BigDecimal num) {
        this.num = num;
    }

    public String getTableCol() {
        return tableCol;
    }

    public void setTableCol(String tableCol) {
        this.tableCol = tableCol;
    }
}
