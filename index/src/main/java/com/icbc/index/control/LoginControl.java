package com.icbc.index.control;


import com.alibaba.fastjson.JSONObject;
import com.icbc.index.model.User;
import com.icbc.index.service.*;
import com.icbc.index.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletRequest;


/*
 * 该控制器负责登陆相关请求
 */

@Controller
public class LoginControl {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //用户服务类
    @Autowired
    UserService userService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RedisService redisService;



    //登录请求
    @RequestMapping(value = "/loginwx", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    String loginwx(@RequestBody User user, HttpServletRequest request) {
        boolean temp = userService.findUser(user.getAccount(), user.getPasswd());
        JSONObject result = new JSONObject();
        if (true) {
            String code = Util.getToken(user.getAccount(), user.getPasswd());
            String token = Util.encode(user.getPasswd(), user, request);
            result.put("result", temp);
            result.put("token", token);
            result.put("code", code);
            redisService.setObj(code, user);
            return result.toJSONString();
        } else {
            result.put("result", false);
            result.put("token", null);
            result.put("code", null);
            return result.toJSONString();
        }


    }

    @RequestMapping(value = "/loginweb", method = RequestMethod.POST)
    public String loginWeb(String code) {

        User user = (User) redisService.getObj(code);

        if (user == null) {

            return "direct:login.html";
        }

        redisService.del(code);

        return "index.html";
    }

    @RequestMapping(value = "/tologinweb")
    public String toLoginWeb() {
        return "login.html";
    }
}

