package com.icbc.index.control;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icbc.index.model.AttrInfo;
import com.icbc.index.model.AttrValue;
import com.icbc.index.service.BusinessSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class vuecontroller {

    @Autowired
    BusinessSaveService businessSaveService;


    @RequestMapping(value = "/listpage" )
    public String vuelist() {
        return "redirect:list.html";
    }

    @CrossOrigin
    @RequestMapping(value = "/list2" )
    @ResponseBody
    public JSONArray axiotest() {
        AttrInfo attrInfo = buildObject();
        AttrInfo attrInfo1 = buildObject2();

        //tostring
        String jsonstr = JSON.toJSONString(attrInfo);
        String jsonstr1 = JSON.toJSONString(attrInfo1);

        //toobject
        JSONObject object = JSON.parseObject(jsonstr);
        JSONObject object1 = JSON.parseObject(jsonstr1);

        //toarray
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(object);
        jsonArray.add(object1);

//        response.setHeader("Access-Control-Allow-Origin","*");
        return  jsonArray;
    }


    public AttrInfo buildObject(){
        //2业务
        AttrInfo attrInfo = new AttrInfo();
        attrInfo.setAttrName("业务");
        AttrValue attrValue = new AttrValue();
        attrValue.setValueName("开卡数");
        AttrValue attrValue2 = new AttrValue();
        attrValue2.setValueName("存款数");
        AttrValue attrValue3 = new AttrValue();
        attrValue3.setValueName("贷款数");
        AttrValue attrValue4 = new AttrValue();
        attrValue4.setValueName("中间业务收入");
        List<AttrValue> list  = new ArrayList<>();
        list.add(attrValue);
        list.add(attrValue2);
        list.add(attrValue3);
        list.add(attrValue4);
        attrInfo.setAttrValueList(list);
        return attrInfo;
    }

    public AttrInfo buildObject2(){
        //1地点
        AttrInfo attrInfo = new AttrInfo();
        attrInfo.setAttrName("地点");
        AttrValue attrValue = new AttrValue();
        attrValue.setValueName("广州");
        AttrValue attrValue2 = new AttrValue();
        attrValue2.setValueName("佛山");
        AttrValue attrValue3 = new AttrValue();
        attrValue3.setValueName("深圳");
        AttrValue attrValue4 = new AttrValue();
        attrValue4.setValueName("东莞");
        AttrValue attrValue5 = new AttrValue();
        attrValue5.setValueName("中山");
        AttrValue attrValue6 = new AttrValue();
        attrValue6.setValueName("珠海");
        AttrValue attrValue7 = new AttrValue();
        attrValue7.setValueName("江门");
        AttrValue attrValue8 = new AttrValue();
        attrValue8.setValueName("肇庆");
        List<AttrValue> list  = new ArrayList<>();
        list.add(attrValue);
        list.add(attrValue2);
        list.add(attrValue3);
        list.add(attrValue4);
        list.add(attrValue5);
        list.add(attrValue6);
        list.add(attrValue7);
        list.add(attrValue8);


        attrInfo.setAttrValueList(list);
        return attrInfo;
    }


    @CrossOrigin
    @RequestMapping(value = "/sendSql",method = RequestMethod.POST)
    @ResponseBody
        public JSONObject sendSql(@RequestBody String tablelist) {

        JSONObject object = JSONObject.parseObject(tablelist);
        System.out.println(object);

        JSONObject objectlist = object.getJSONObject("tablelist");
        //System.out.println("hello" + objectlist);

        //给getTableByJson方法处理
        JSONObject object1 =businessSaveService.getDaysNumByBankName(objectlist);
//        response.setHeader("Access-Control-Allow-Origin","*");
        return object1;
    }
    /**
     * {"tablelist":{
     *     "list":[
     *     {
     *         "attrName":"业务",
     *         "attrValueList":[{"valueName":"开户数"},{"valueName":"贷款数"}]
     *     },
     *     {
     *         "attrName":"地点",
     *         "attrValueList":[{"valueName":"广州"},{"valueName":"深圳"}]
     *     },
     *     {
     *         "attrName":"时间",
     *          "attrValueList":[{"valueName":"2019-01-01"},{"valueName":"2019-07-01"}]
     *     }
     *     ]
     * }}
     * object:id:field
     * 广州:2019-01-01:cardNumber
     * 广州:2019-01-01:
     * 2019-01-01:广州:cardNumber
     */

}
