package com.icbc.index.service;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;


/*
* 调用微服务接口（python）
* @author mychen
* */
@FeignClient(name ="PYTHONSIDECAR")
public interface PythonInvoke {

    @RequestMapping(value = "/recommend/",method = RequestMethod.POST,
            headers = {"content-type=application/x-www-form-urlencoded"}
            ,consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public  String recommend(MultiValueMap<String,Object> user);

    @RequestMapping(value = "/jieba")
    public String cutWord(@RequestParam("raw")String raw);
}
