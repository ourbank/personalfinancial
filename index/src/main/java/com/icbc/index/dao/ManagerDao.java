package com.icbc.index.dao;

import com.icbc.index.mapper.ManagerMapper;
import com.icbc.index.model.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/*
 * 用户数据访问类
 * @author mychen*/
@Repository
public class ManagerDao {

    @Autowired
    ManagerMapper managerMapper;


    public Manager findUser(String account) {

        return managerMapper.findUser(account);
    }


}
