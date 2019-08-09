package com.icbc.index.service;

import com.icbc.index.dao.ManagerDao;

import com.icbc.index.entity.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {


    @Autowired
    ManagerDao managerDao;

    public boolean isLegal(Manager temp){

        Manager manager = managerDao.findUser(temp.getAccount());
        if (manager ==null)
            return false;
        else if (manager.getPassword().equals(temp.getPassword()))
            return true;
        else
            return false;
    }
}
