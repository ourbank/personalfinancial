package com.icbc.index.control;

import com.icbc.index.service.AnalyseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
public class AnalyseController {

    Logger logger = LoggerFactory.getLogger(AnalyseController.class);
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
    public String getwordcloud(@RequestParam("business") String bus,@RequestParam("period") String period){

        logger.debug("根据时间周期和业务控制器："+bus+"<--->"+period);
        return analyseService.getwordcloudByBus(bus,period);
    }
}
