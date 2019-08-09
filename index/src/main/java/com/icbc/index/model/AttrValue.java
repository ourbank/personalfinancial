package com.icbc.index.model;

import java.io.Serializable;

public class AttrValue implements Serializable {
    public String valueName;

    @Override
    public String toString() {
        return "AttrValue{" +
                "valueName='" + valueName + '\'' +
                '}';
    }

    public AttrValue() {
    }

    public AttrValue(String valueName) {
        this.valueName = valueName;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }
}
