package com.icbc.personalfinancial.Controller;


import com.icbc.personalfinancial.common.createtestdata.RandomValue;
import com.icbc.personalfinancial.entity.Card;
import com.icbc.personalfinancial.entity.User;
import com.icbc.personalfinancial.service.CardService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.undertow.UndertowWebServer;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.HttpResource;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.math.BigDecimal;
import java.sql.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping(value = "/card")
public class CardController {

    @Autowired
    private CardService cardService;


    @RequestMapping(value = "/getAccountName/{id}",method = RequestMethod.GET)
    public List<String> getAccountNameById(@PathVariable("id") Integer id){
        List<String> name = this.cardService.findAccountNameByAccountID(id);
        System.out.println(id);
        if(name == null){
            throw new RuntimeException("查询错误");
        }
        System.out.println(name);
        return name;
    }

    @RequestMapping(value = "/getName/{cardId}",method = RequestMethod.GET)
    public List<String> getAccountName(@PathVariable("cardId") String cardID){
        List<String> name = this.cardService.findAccountNameByCardId(cardID);
        System.out.println(cardID);
        if(name == null){
            throw new RuntimeException("查询错误");
        }
        System.out.println(name);
        return name;
    }

    @RequestMapping(value = "/insert",method = RequestMethod.GET)
    public String inserUser(){
        User user = new User();
//        user.setId(4);
//        user.setUserName("七七");
//        user.setUserId("56161548615486138");
//        user.setAddr("梅州");
//        user.setMobile("1513645822");
//        System.out.println(user);

        Integer id = 6;
        for (int i = 0; i <1000 ; i++) {
            id = 6 + i;

            user.setId(id);
            user.setUserName(RandomValue.getChineseName());
            user.setUserId(RandomValue.getIdNo(true));
            user.setAddr(RandomValue.getaddr());
            user.setMobile(RandomValue.getTel());
            System.out.println(user);
            this.cardService.insertUser(user);
        }

        return "succes";
    }

    @RequestMapping(value = "/update",method = RequestMethod.GET)
    public String updateUser(){
        User user = new User();
        Integer id ;
        for (int i = 1; i <5 ; i++) {
            id = i;

            user.setId(id);
            user.setUserName(RandomValue.getChineseName());
            user.setUserId(RandomValue.getIdNo(true));
            user.setAddr(RandomValue.getaddr());
            user.setMobile(RandomValue.getTel());
//            user.setId(5);
//            user.setUserName("七七");
//            user.setUserId("56161548615486138");
//            user.setAddr("梅州");
//            user.setMobile("1513645822");
            System.out.println(user);
            this.cardService.updateUser(user);
        }

        return "succes";
    }

    @RequestMapping(value = "/delete/{userId}", method = RequestMethod.GET)
    public  String  deleteUser(@PathVariable("userId") String userId){
        this.cardService.deleteUser(userId);

        return  "success";
    }


    @RequestMapping(value = "/insertcard",method = RequestMethod.GET)
    public  String  insertCard(){
        Card card = new Card();
        BigDecimal big = new BigDecimal("0.0");
        //i为card所属account表的id
        for (int i = 3; i < 1003; i++) {
            card.setId(i-2);
            card.setBelongAccountId(i);
            //1.根据id查询userId再根据userId查询地点addr
            String userId = cardService.findUserIdByAccounID(i);
            String addr = cardService.findAddrByUserId(userId);
            System.out.println(addr);
            //2.根据addr查询地点，根据地点给出bankId
            card.setBankId(cardService.findBankIdByBankAddr(addr));

            //3.随机出cardType，根据cardType随机出cardId
            int cardType = RandomValue.getCardType();
            card.setCardType(cardType);
            card.setCardId(RandomValue.getCardIdBuType(cardType));

            //4.随机出时间
            card.setCreateTime(RandomValue.getRandomDate("2009-07-01","2019-07-01"));

            //5.默认serviceCharge为0

            card.setServiceCharge(big);

            //6.插入数据
            System.out.println(card);
            cardService.insertcard(card);
        }


        return "success";
    }

    @RequestMapping(value = "/guangzhoucard",method = RequestMethod.GET)
    public  String  insertGuangzhou(){
        Card card = new Card();
//        //i为card所属account表的id
//        for (int i = 3; i < 1003; i++) {
//            card.setId(i-2);
//            card.setBelongAccountId(i);
//            //1.根据id查询userId再根据userId查询地点addr
//            String userId = cardService.findUserIdByAccounID(i);
//            String addr = cardService.findAddrByUserId(userId);
//            System.out.println(addr);
//            //2.根据addr查询地点，根据地点给出bankId
//            card.setBankId(cardService.findBankIdByBankAddr(addr));
//
//            //3.随机出cardType，根据cardType随机出cardId
//            int cardType = RandomValue.getCardType();
//            card.setCardType(cardType);
//            card.setCardId(RandomValue.getCardIdBuType(cardType));
//
//            //4.随机出时间
//            card.setCreateTime(RandomValue.getRandomDate("2009-07-01","2019-07-01"));
//
//            //5.默认serviceCharge为0
//            card.setServiceCharge(0.0);
//
//            //6.插入数据
//            System.out.println(card);
//            cardService.insertcard(card);
//        }
        BigDecimal big = new BigDecimal("0.0");
        Random r = new Random();
        for (int i = 18001; i < 36000; i++) {
           card.setId(i);
           card.setBelongAccountId(r.nextInt(995)+5);
           card.setBankId(4);
           card.setServiceCharge(big);
            //3.随机出cardType，根据cardType随机出cardId
            int cardType = RandomValue.getCardType();
            card.setCardType(cardType);
            card.setCardId(RandomValue.getCardIdBuType(cardType));

            //4.随机出时间
            card.setCreateTime(RandomValue.getRandomDate("2009-07-01","2019-07-01"));

            System.out.println(card);
            cardService.insertcard(card);
        }

        return "success";
    }

    @RequestMapping(value = "/findCountByBankAndTime" ,method = RequestMethod.POST)
    public JSONObject findCountByBankAndTime(HttpServletResponse response, @RequestParam(value = "date1") String  date1, @RequestParam(value = "date2") String  date2, @RequestParam(value = "bankName") String bankName) {

        System.out.println(date1);
        System.out.println(date2);
        System.out.println(bankName);
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date start = null;
//        Date end = null;
//        try {
//            start = format.parse("2018-01-01 00:00:00");
//            end =  format.parse("2019-01-01 00:00:00");
//
//            System.out.println(start);
//            System.out.println(end);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        int i = cardService.findCountByBankAndTime(date1,date2,bankName);
        System.out.println(i);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sum",i);
        System.out.println(jsonObject);
        response.setHeader("Access-Control-Allow-Origin","*");
        return jsonObject;
    }


    @RequestMapping(value = "/pr" , method =  RequestMethod.POST)
    public void  print(HttpServletResponse response , HttpServletRequest request, @RequestParam(value = "cityList") String[] cities,
                       @RequestParam(value = "JList") String[] JList,@RequestParam(value = "startTime") String startTime,
                       @RequestParam(value = "endTime") String endTime
                       ){
//        String array =  request.getParameter("cityList");
//        List<String> cities = JSONArray.fromObject(array);
        System.out.println(cities[0]);
        System.out.println(JList[0]);
        System.out.println(startTime);
        System.out.println(endTime);
        response.setHeader("Access-Control-Allow-Origin", "*");;
        System.out.println("结束");

    }
}
