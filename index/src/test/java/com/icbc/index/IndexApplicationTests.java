package com.icbc.index;
import com.icbc.index.service.CardService;
import com.icbc.index.util.TimeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.smartcardio.Card;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IndexApplicationTests {

    @Autowired
    CardService cardService;

    @Test
    public void contextLoads() {
        //cardService.getDataBy3factors(null);
        TimeUtil.getTimePeriod("2018-2","2019-10");

    }


}
