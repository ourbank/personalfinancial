package com.icbc.index.control;

import com.icbc.index.service.PredictService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
public class PredictController {

    @Autowired
    PredictService predictService;

    /**
     * 获取上一年/季度/月的各大城市占比
     * @param period 标识年/季度/月
     * @param buss 标识是某种业务
     * @return json字符串
     */
    @RequestMapping(value = "/getscale")
    @ResponseBody
    public String getScaleByPeriod(@RequestParam("period") String period,@RequestParam("business") String buss){
        return predictService.getScaleByPeriod(period,buss);
    }

    /**
     * 获取默认的预测数据，数据默认是 2历史 + 4 用户要求
     * @return json字符串
     */
    @RequestMapping(value = "/getdefaultpredict")
    @ResponseBody
    public String getdefaultpredict(){
        System.out.println("getdefaultpredict");
        return predictService.getdefaultpredict();
    }

    @RequestMapping(value = "/getsimplepredict")
    @ResponseBody
    public String getsimplepredict(@Param(value = "business")String business){
        System.out.println("getsimplepredict");
        return predictService.getSimplePredictByBus(business);
    }
}
