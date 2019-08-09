package com.icbc.index.control;

import com.icbc.index.service.AnalyseService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
public class AnalyseController {
    @Autowired
    AnalyseService analyseService;

    /**
     * 根据业务和时间单位获取数据
     * @param bus
     * @param period
     * @return
     */
    @RequestMapping(value = "/getwordcloud")
    @ResponseBody
    String getwordcloud(@RequestParam("business") String bus,@RequestParam("period") String period){
        System.out.println("getwordcloud");
        return analyseService.getwordcloudByBus(bus,period);
    }
}
