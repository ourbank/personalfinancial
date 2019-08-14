package com.icbc.index.control;


import com.icbc.index.service.Hello;
import com.icbc.index.service.PythonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 *  该控制器负责调用python微服务
 */
@Controller
public class PythonController {

    @Resource
    Hello hello;


    @Autowired
    PythonService pythonService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public @ResponseBody
    String test() {
        return hello.test();

    }

}
