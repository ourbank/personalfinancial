package com.icbc.index.vo;

/**
 * 该类为配合前端字符与 数据格式要求
 */
public class WordCloudVo {
    private String name;
    private int value;
    public WordCloudVo(){}

    public WordCloudVo(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
