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




/*
 * 该控制器负责登陆相关请求
 */

@Controller
public class LoginControl {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //用户服务类
    @Autowired
    ManagerService managerService;


    @Autowired
    RedisService redisService;


    //登录请求
    @RequestMapping(value = "/loginwx", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    String loginwx(@RequestBody Manager manager) {
        boolean temp = managerService.isLegal(manager);
        JSONObject result = new JSONObject();
        if (temp) {
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


    @RequestMapping(value = "/loginweb", method = RequestMethod.POST)

    public String loginWeb(@RequestParam(name = "code") String code) {


        Manager manager = (Manager) redisService.getObj(code);

        if (manager == null) {

            return "redirect:login.html";
        }

        redisService.setObj("token",manager.getToken());

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

        String token = (String) redisService.getObj("token");

        return token;

    }

}

