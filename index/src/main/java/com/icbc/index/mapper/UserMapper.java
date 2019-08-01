package com.icbc.index.mapper;

import com.icbc.index.model.User;

/*
* 用户数据访问接口，对应于 /resources/mapper下的xml文件，执行具体的sql语句
* @author
* */

public interface UserMapper {

    public User findUser(String account);
}
