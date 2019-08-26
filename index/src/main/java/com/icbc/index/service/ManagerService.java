package com.icbc.index.service;

import com.alibaba.fastjson.JSONObject;
import com.icbc.index.dao.ManagerDao;

import com.icbc.index.entity.Manager;
import com.icbc.index.util.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {


    @Autowired
    ManagerDao managerDao;

    @Autowired
    RedisService redisService;

    public String isLegal(Manager temp) {

        Manager manager = managerDao.findUser(temp.getAccount());
        JSONObject result = new JSONObject();
        if (manager.getPassword().equals(temp.getPassword())) {

            String code = LoginUtil.getCode(manager);
            String token = LoginUtil.encode(manager);

            manager.setToken(token);
            redisService.setObj(code, manager, 10);

            result.put("result", true);
            result.put("token", token);
            result.put("code", code);

            return result.toJSONString();
        } else {
                result.put("result", false);
                result.put("token", null);
                result.put("code", null);
                return result.toJSONString();
            }
        }

    public String getUserBank(String account) {
        return managerDao.findUserBank(account);
    }
}
