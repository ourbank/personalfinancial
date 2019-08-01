package com.icbc.index.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/*
* 调用微服务接口（python）
* @author mychen
* */
@FeignClient(name ="PYTHONSERVICE")
public interface Hello {

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test();
}
