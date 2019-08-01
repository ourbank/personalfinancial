package com.icbc.index.dao;

import com.icbc.index.mapper.InquiryOperation;
import com.icbc.index.mapper.UserMapper;
import com.icbc.index.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
* 用户数据访问类
* @author mychen*/
@Component
public class UserDao {

    @Autowired
    UserMapper userMapper;



    public User findUser(String account){

        return userMapper.findUser(account);
    }


}
