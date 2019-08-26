package com.icbc.index.vo;

/**
 * 该类为配合前端字符与数据格式要求
 */
public class WordCloudVo {
    private String name;
    private double value;
    public WordCloudVo(){}

    public WordCloudVo(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
