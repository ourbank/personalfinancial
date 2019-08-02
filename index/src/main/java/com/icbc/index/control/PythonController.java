package com.icbc.index.control;

import com.icbc.index.model.CardData;
import com.icbc.index.service.Hello;
import com.icbc.index.service.PythonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 *  该控制器负责调用python微服务
 */
public class PythonController {

    @Autowired
    Hello hello;


    @Autowired
    PythonService pythonService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public @ResponseBody
    String test() {
        return hello.test();

    }

    @RequestMapping(value = "/t")
    public @ResponseBody
    List<CardData> t() {
        return pythonService.getCardMount();
    }
}
