package com.icbc.index.mapper;

/*
* 用户数据访问接口，对应于 /resources/mapper下的xml文件，执行具体的sql语句
* @author
* */


import com.icbc.index.entity.Manager;

public interface ManagerMapper {

    public Manager findUser(String account);
}
