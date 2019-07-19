package com.icbc.personalfinancial.dao;


import com.icbc.personalfinancial.entity.Card;
import com.icbc.personalfinancial.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.SplittableRandom;

@Mapper
public interface CardMapper {

    /**
     * 根据cardId找到所属用户
     *
     * @param  cardId
     * @return
     */
    @Select("select accountName  " +
            "from card c,account a  " +
            "where c.cardId = #{cardId} AND c.belongAccountId = a.id ")
    List<String> findAccountNameByCardID(@Param("cardId") String cardId) ;


    @Select("SELECT accountName FROM account WHERE id = #{id}")
    List<String> fiandaccoountNameByAccountId(@Param("id") Integer id);

    /**
     * 向user表插入数据
     *
     * @param user
     * @return
     */
    @Insert({"INSERT INTO user(id, userName, userId, addr, mobile) ",
            "VALUES (#{id},#{userName},#{userId},#{addr},#{mobile})"})
    int insertUser(User user);

    /**
     * 向user表更新数据
     *
     * @param user
     * @return
     */
    @Update({"UPDATE user ", "SET userName = #{userName},userId = #{userId},addr = #{addr},mobile = #{mobile} ",
            " WHERE id=#{id}"})
    int updateUser(User user);

    /**
     * 根据身份证号userId删除数据
     *
     * @param userId
     * @return
     */
    @Delete( "DELETE FROM user WHERE  userId = #{userId}")
    int deleteUser(String userId);

    /**
     * 根据account表的id查询userId
     * @param id
     * @return
     */
    @Select("SELECT userId FROM account WHERE  id = #{id}")
    String findUserIdbByAccountId(@Param("id") Integer id);

    /**
     * 从user表根据userId查询addr
     * @param userId
     * @return
     */
    @Select("SELECT addr FROM user WHERE userId = #{userId}")
    String  findAddrByUserId(@Param("userId") String  userId);


    /**
     * 从bank表根据Addr查询BankId
     *
     * @param addr
     * @return
     */
    @Select("SELECT id FROM bank WHERE  bankAddr = #{addr}")
    int findBankIdByBankAddr(@Param("addr") String  addr);


    /**
     * 插入card表
     * @param card
     * @return
     */
    @Insert("INSERT INTO card(id, cardId, cardType, belongAccountId, createTime, serviceCharge,bankId )" +
            " VALUES (#{id},#{cardId},#{cardType},#{belongAccountId},#{createTime}, #{serviceCharge}, #{bankId})")
    int insertCard(Card card);


    /**
     * 根据分行名和时间段查询开卡数
     * @param date1
     * @param date2
     * @param bankName
     * @return
     */
    @Select("select count(card.id) " +
            " from card " +
            " inner join bank on card.bankId = bank.id  " +
            " WHERE card.createTime > #{date1,jdbcType = DATE}  AND card.createTime < #{date2,jdbcType = DATE} " +
            " group by bankName   " +
            " having bankName = #{bankName} ")
    Integer findCountByBankAndTime(@Param("date1") String  date1, @Param("date2") String date2, @Param("bankName") String  bankName);

}
