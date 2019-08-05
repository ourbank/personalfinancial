package com.icbc.index.service;

import com.icbc.index.mapper.CardDao;
import com.icbc.index.model.CardData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.Callable;

@Async
public class cardSearchThred implements Callable {


    private CardDao cardDao;
    private String city;
    private String factor;
    private String local_start;
    private String local_end;

    @Override
    public String toString() {
        return "cardSearchThred{" +
                "cardDao=" + cardDao +
                ", city='" + city + '\'' +
                ", factor='" + factor + '\'' +
                ", local_start='" + local_start + '\'' +
                ", local_end='" + local_end + '\'' +
                '}';
    }

    public cardSearchThred(CardDao cardDao,String city, String factor, String local_start, String local_end) {
        this.cardDao = cardDao;
        this.city = city;
        this.factor = factor;
        this.local_start = local_start;
        this.local_end = local_end;
    }

    @Override
    public Object call() throws Exception {
        List<CardData> out = null;
        switch(factor){
            case "开卡数":
                out =  cardDao.getDataBy3factors(city,local_start,local_end);
                System.out.println(out.size());
                break;
            case "存款数":
                break;
            case "贷款数":
                break;
            case "中间业务收入":
                break;

        }
        return out;
    }
}
