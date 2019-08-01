package com.icbc.index.dao;

import com.icbc.index.mapper.InquiryOperation;
import com.icbc.index.model.CardData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CardMountDao {

    @Autowired
    InquiryOperation inquiryOperation;

    public List<CardData> getCardMount(){

        return inquiryOperation.getCardMount("广州分行");
    }
}
