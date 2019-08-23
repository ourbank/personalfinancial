package com.icbc.index.mapper;

import com.icbc.index.model.CardData;
import com.icbc.index.model.CoreInquiryParam;

import java.util.List;

public interface CoreInquiry {

    public List<CardData> inquiryByYears(CoreInquiryParam sql);

    public List<CardData> inquiryByPeriod(CoreInquiryParam param);

    public List<CardData> inquiryBySeason(CoreInquiryParam param);

    public List<CardData> inquiryByMonths(CoreInquiryParam sql);

    public List<CardData> inquiryBySeasons(CoreInquiryParam sql);



}
