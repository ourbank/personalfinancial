package com.icbc.personalfinancial.service;


import com.icbc.personalfinancial.common.createtestdata.RedisUtil;
import com.icbc.personalfinancial.dao.CardMapper;
import com.icbc.personalfinancial.entity.Card;
import com.icbc.personalfinancial.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class CardService {

    @Resource
    private CardMapper cardMapper;

    public List<String> findAccountNameByCardId(String cardId){ return this.cardMapper.findAccountNameByCardID(cardId); }

    public void insertUser(User user){ this.cardMapper.insertUser(user); }

    public void updateUser(User user){
        this.cardMapper.updateUser(user);
    }

    public void deleteUser(String userId){
        this.cardMapper.deleteUser(userId);
    }

    public String findUserIdByAccounID(Integer id){
        return this.cardMapper.findUserIdbByAccountId(id);
    }

    public String findAddrByUserId(String userId){ return this.cardMapper.findAddrByUserId(userId); }

    public int findBankIdByBankAddr(String addr){
        return this.cardMapper.findBankIdByBankAddr(addr);
    }

    public void insertcard(Card card){
        this.cardMapper.insertCard(card);
    }

    public List<String> findAccountNameByAccountID(Integer id){
        return this.cardMapper.fiandaccoountNameByAccountId(id);
    }

    public Integer findCountByBankAndTime(String  date1, String date2, String  bankName){
        return  this.cardMapper.findCountByBankAndTime(date1,date2,bankName);
    }


}
