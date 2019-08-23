package com.icbc.index.dao;

import com.icbc.index.entity.Manager;
import com.icbc.index.mapper.ManagerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;


/*
 * 用户数据访问类
 * @author mychen*/
@Repository
public class ManagerDao {

    @Resource
    ManagerMapper managerMapper;


    public Manager findUser(String account) {

        return managerMapper.findUser(account);
    }

    public String findUserBank(String account){
        return managerMapper.findUserBank(account);
    }


}
