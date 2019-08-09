package com.icbc.index.model;

import java.io.Serializable;
import java.util.List;


public class AttrInfo implements Serializable {
    public String attrName;
    public List<AttrValue> attrValueList;

    @Override
    public String toString() {
        return "AttrInfo{" +
                "attrName='" + attrName + '\'' +
                ", attrValueList=" + attrValueList +
                '}';
    }

    public AttrInfo() {
    }

    public AttrInfo(String attrName, List<AttrValue> attrValueList) {
        this.attrName = attrName;
        this.attrValueList = attrValueList;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public List<AttrValue> getAttrValueList() {
        return attrValueList;
    }

    public void setAttrValueList(List<AttrValue> attrValueList) {
        this.attrValueList = attrValueList;
    }
}
