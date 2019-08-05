package com.icbc.python.outService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OutServeice {

   @Autowired
   RestTemplate restTemplate;

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test(){
        return restTemplate.getForObject("http://127.0.0.1:8000/health",String.class);

    }
}
