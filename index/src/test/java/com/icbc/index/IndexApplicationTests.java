package com.icbc.index;
import com.icbc.index.service.BusinessSaveService;
import com.icbc.index.service.CardService;
import com.icbc.index.util.TimeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.smartcardio.Card;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IndexApplicationTests {

    @Autowired
    CardService cardService;

    @Autowired
    BusinessSaveService businessSaveService;

    @Test
    public void contextLoads() {
        //cardService.getDataBy3factors(null);
        TimeUtil.getTimePeriod("2018-2","2019-10");

    }

    @Test
    public void businessSave(){
        HashMap<String,Object> map = new HashMap<>();
        List<String> city = Arrays.asList("深圳","佛山");
        List<String> business = Arrays.asList("开卡数","存款数");
        map.put("city", city);
        map.put("business", business);
        map.put("startTime", "2009-07-01 00:00:00");
        map.put("endTime", "2015-06-01 00:00:00");
        System.out.println(map);
//        businessSaveService.getBusiness(map);
    }


}
