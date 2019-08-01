package com.icbc.index.service;

import com.icbc.index.dao.UserDao;
import com.icbc.index.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public boolean findUser(String account,String passwd){

        User user =userDao.findUser(account);
        if (user==null)
            return false;
        else if (user.getPasswd().equals(passwd))
            return true;
        else
            return false;
    }
}
