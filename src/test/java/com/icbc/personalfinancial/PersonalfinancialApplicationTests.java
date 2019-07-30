package com.icbc.personalfinancial;

import com.icbc.personalfinancial.common.createtestdata.Insert;
import com.icbc.personalfinancial.common.createtestdata.RandomValue;
import com.icbc.personalfinancial.common.createtestdata.RedisUtil;
import com.icbc.personalfinancial.entity.User;
import com.icbc.personalfinancial.service.CardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.text.ParseException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonalfinancialApplicationTests {

    @Autowired
    RedisUtil  redisUtil;

    @Autowired
    CardService cardService;

    @Test
    public void contextLoads() {

        Jedis jedis1 = redisUtil.getJedis();
        jedis1.set("name","Jack");
        System.out.println(jedis1.get("name"));
    }

    @Test
    public void insert()  {
        Insert insert = new Insert();
        insert.insertMetal();
    }



    @Test
    public void updateUser(){
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

    }


}
