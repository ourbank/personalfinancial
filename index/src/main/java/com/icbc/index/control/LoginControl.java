package com.icbc.index.control;


import com.alibaba.fastjson.JSONObject;
import com.icbc.index.entity.Manager;
import com.icbc.index.service.*;
import com.icbc.index.util.LoginUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;




/*
 * 该控制器负责登陆相关请求
 */

@Controller
@CrossOrigin
public class LoginControl {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //用户服务类
    @Autowired
    ManagerService managerService;


    @Autowired
    RedisService redisService;

    //记录所有用户的 token 值
    Map<String,Manager> allUser =new HashMap();
    //登录请求
    @RequestMapping(value = "/loginwx", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    String loginwx(@RequestBody Manager manager) {
        String  result = managerService.isLegal(manager);
        return result;

    }


    @RequestMapping(value = "/loginweb", method = RequestMethod.POST)

    public String loginWeb(@RequestParam(name = "code") String code) {

        Manager manager = (Manager) redisService.getObj(code);

        if (manager == null) {

            return "redirect:login.html";
        }
        redisService.delObj(code);
        allUser.put(manager.getToken(),manager);
        redisService.setObj("token",allUser);
        redisService.setObj("register",manager.getToken());
        redisService.setObj("id",manager.getId());
        return "redirect:index.html";
    }

    @RequestMapping(value = "/tologinweb")

    public String toLoginWeb() {

        return "login.html";
    }

    /**
     * PC 端用户认证
     */
    @GetMapping("/registerwebsocket")
    public @ResponseBody String beforeWebSocket(){

        String token = (String) redisService.getObj("register");
        return token;

    }

}

