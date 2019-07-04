package com.icbc.kfzx.personalfinancial.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value="/test")
public class testController {

    @RequestMapping(value = "/sendData/{data}")
    @ResponseBody
    String testReceice(Model model,String data){
        System.out.println(data);
        return "SUCCESS-LIMK";
    }

    @RequestMapping(value = "/toHome")
    String thymeleaf(Model model){
        model.addAttribute("name","limk");
        return "hello";
    }

}
