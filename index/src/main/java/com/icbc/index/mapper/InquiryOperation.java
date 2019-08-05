package com.icbc.index.mapper;

import com.icbc.index.model.CardData;

import java.util.List;


public interface InquiryOperation {

    public List<CardData> getCardMount(String bankname);
}
