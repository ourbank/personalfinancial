package com.icbc.index.control;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icbc.index.model.AttrInfo;
import com.icbc.index.model.AttrValue;
import com.icbc.index.service.BusinessSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class vuecontroller {

    @Autowired
    BusinessSaveService businessSaveService;


//    @RequestMapping("/list")
//    public void vue(){
//
//    }

//    @RequestMapping(value = "/list" )
//    public JSONArray vuedemo(HttpServletResponse response) {
//
//       //jsonObject: attrinfo
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("attrName","地点");
//
//        //jsonArray:attrvaluelist
//        JSONObject jsonObject1 = new JSONObject();
//        jsonObject1.put("valueName","广州");
//        JSONObject jsonObject2 = new JSONObject();
//        jsonObject2.put("valueName","汕尾");
//        JSONArray jsonArray = new JSONArray();
//        jsonArray.put(jsonObject1);
//        jsonArray.put(jsonObject2);
//
//        JSONObject jsonObject3 = new JSONObject();
//        jsonObject3.put("attrValueList",jsonArray);
//
//        JSONArray jsonArray1 = new JSONArray();
//        jsonArray1.put(jsonObject);
//        jsonArray1.put(jsonObject3);
//
//        System.out.println(jsonObject);
//        response.setHeader("Access-Control-Allow-Origin","*");
//        return  jsonArray1;
//    }


    @RequestMapping(value = "/listpage" )
    public String vuelist() {
        return "redirect:list.html";
    }

    @RequestMapping(value = "/list2" )
    @ResponseBody
    public JSONArray axiotest(HttpServletResponse response) {
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

        response.setHeader("Access-Control-Allow-Origin","*");
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
        attrValue2.setValueName("深圳");
        List<AttrValue> list  = new ArrayList<>();
        list.add(attrValue);
        list.add(attrValue2);
        attrInfo.setAttrValueList(list);
        return attrInfo;
    }


    @RequestMapping(value = "/sendSql",method = RequestMethod.POST)
    @ResponseBody
        public JSONObject sendSql(@RequestBody String tablelist) {

        JSONObject object = JSONObject.parseObject(tablelist);
        System.out.println(object);

        JSONObject objectlist = object.getJSONObject("tablelist");
        //System.out.println("hello" + objectlist);

        //给getTableByJson方法处理
        businessSaveService.getDaysNumByBankName(objectlist);
        JSONObject object1 = new JSONObject();
        object1.put("return","200");
        return object1;
    }
    //String substring = list.substring(list.indexOf("["));
    //String x = URLDecoder.decode(list,"utf-8");

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
