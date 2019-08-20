package com.icbc.index.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.icbc.index.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AnalysisController {

    @Autowired
    AnalysisService analysisService;

    @CrossOrigin
    @RequestMapping(value = "/analysis/head",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getHead(@RequestBody String s) {
        System.out.println(s);
        JSONObject object = new JSONObject();

        switch (s){
            case "card_bill": object.put("colHead","存款类型");object.put("col", "今日存款金额");break;
            case "intermediate_bill": break;
            case "deposit_daily": break;
            case "loan_daily": break;
            default:
        }

        return object;
    }

    @CrossOrigin
    @RequestMapping(value = "/analysis/sum",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getSum(@RequestBody JSONObject jsonObject) {
        Map<String,Long> map = new HashMap<>();
        map.put("sum",analysisService.getDaySum(jsonObject));
        return JSONObject.parseObject(JSON.toJSONString(map));
    }
}
