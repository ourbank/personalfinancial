package com.icbc.personalfinancial.service;


import com.icbc.personalfinancial.dao.BillMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

@Resource
public class BillService {

    @Autowired
    private BillMapper billMapper;


}
