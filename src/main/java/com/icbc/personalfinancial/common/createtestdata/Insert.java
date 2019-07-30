package com.icbc.personalfinancial.common.createtestdata;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Insert {

    final String driver = "com.mysql.jdbc.Driver";
    final String url = "jdbc:mysql://149.28.26.45:3306/test";
    final String user = "root";
    final String password = "Kfzx_gzsb@01";


    //几线城市的bankid
    int[][] BANKCITY_LEV = {{3,4,6},{5,7,8,11},{9,10,12,14,15,20},{2,13,15,17,19,21,22},{18}};
    final static int CARDNUMBER_LEV1  = 20;
    final static int CARDNUMBER_LEV2  = 16;
    final static int CARDNUMBER_LEV3  = 12;
    final static int CARDNUMBER_LEV4  = 8;
    final static int CARDNUMBER_LEV5  = 4;
    final static int METALNUMBER_LEV1  = 10;
    final static int METALNUMBER_LEV2  = 8;
    final static int METALNUMBER_LEV3  = 6;
    final static int METALNUMBER_LEV4  = 4;
    final static int METALNUMBER_LEV5  = 2;


    Connection conn = null;
    PreparedStatement pst =  null;
    Random r = new Random();
    long beginTime = 0;
    long endTime = 0;
    int ccc= 1;



    public void insertUser(){
        try {
            Class.forName(driver);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);
            if(conn != null) {
                System.out.println("获取连接成功");
                beginTime = new Date().getTime();//开始计时
                String sqlPrefix = "INSERT INTO user(  id, userName, userId, addr, mobile) VALUES   ";
                // 保存sql后缀
                StringBuffer suffix = new StringBuffer();
                // 设置事务为非自动提交
                conn.setAutoCommit(false);
                // 比起st，pst会更好些
                pst = (PreparedStatement) conn.prepareStatement("");//准备执行语句
                // 外层循环，总提交事务次数
                int id = 11005;
                for (int i = 1; i <= 90; i++) {
                    suffix = new StringBuffer();
                    // 第j次提交步长
                    for (int j = 1; j <= 1000; j++) {
                        // 构建SQL后缀
                        id +=1;
                        suffix.append("("+ id +",'"+RandomValue.getChineseName() +"','"+ RandomValue.getIdNo(true)  +"','"+RandomValue.getaddr()  +"','"+ RandomValue.getTel() +"'),");
                    }
                    // 构建完整SQL
                    String sql = sqlPrefix + suffix.substring(0, suffix.length() - 1);
                    System.out.println(sql);
                    // 添加执行SQL
                    pst.addBatch(sql);
                    // 执行操作
                    pst.executeBatch();
                    // 提交事务
                    conn.commit();
                    // 清空上一次添加的数据
                    suffix = new StringBuffer();
                }
                endTime = new Date().getTime();//开始计时
            }else {
                System.out.println("数据库连接失败");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("com.mysql.jdbc.Driver驱动类没有找到");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库地址错误");
        }finally {//释放资源
            System.out.println("插入成功，所有时间："+ (endTime-beginTime));
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void insertOneCard(){
        int temp = 0;
        Integer id = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Class.forName(driver);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);
            if(conn != null) {
                System.out.println("获取连接成功");
                beginTime = new Date().getTime();//开始计时
                String sqlPrefix = "INSERT INTO card( id, cardId, cardType, belongAccountId, createTime, serviceCharge,bankId ) VALUES   ";
                // 保存sql后缀
                StringBuffer suffix = new StringBuffer();
                // 设置事务为非自动提交
                conn.setAutoCommit(false);
                // 比起st，pst会更好些
                pst = (PreparedStatement) conn.prepareStatement("");//准备执行语句
                // 外层循环，总提交事务次数
                for (int i = 1; i <= 101; i++) {
                    suffix = new StringBuffer();
                    // 第j次提交步长
                    for (int j = 1; j <= 1000; j++) {
                        // 构建SQL后缀
                        temp = RandomValue.getCardType();
                        String time = format.format(RandomValue.getRandomDate("2009-07-01","2019-07-01"));
                        System.out.println(time);
                        id += 1;
                        int bankId = r.nextInt(22)+1;
                        suffix.append("("+id +",'"+ RandomValue.getCardIdBuType(temp).toString()+"',"+temp +","+id+",'"+time+"',"+0.0+","+bankId+"),");
                    }
                    // 构建完整SQL
                    String sql = sqlPrefix + suffix.substring(0, suffix.length() - 1);
                    // 添加执行SQL
                    pst.addBatch(sql);
                    // 执行操作
                    pst.executeBatch();
                    // 提交事务
                    conn.commit();
                    // 清空上一次添加的数据
                    suffix = new StringBuffer();
                }
                endTime = new Date().getTime();//开始计时
            }else {
                System.out.println("数据库连接失败");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("com.mysql.jdbc.Driver驱动类没有找到");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库地址错误");
        }finally {//释放资源
            System.out.println("插入成功，所有时间："+ (endTime-beginTime));
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void inserCardRan(){
//        final String driver = "com.mysql.jdbc.Driver";
//        final String url = "jdbc:mysql://149.28.26.45:3306/test";
//        final String user = "root";
//        final String password = "Kfzx_gzsb@01";
//        Connection conn = null;
//        PreparedStatement pst =  null;
//        Random r = new Random();
        int temp = 0;
        Integer id = 22605;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Class.forName(driver);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);
            if(conn != null) {
                System.out.println("获取连接成功");
                beginTime = new Date().getTime();//开始计时
                String sqlPrefix = "INSERT INTO card(  cardId, cardType, belongAccountId, createTime, serviceCharge,bankId ) VALUES   ";
                // 保存sql后缀
                StringBuffer suffix = new StringBuffer();
                // 设置事务为非自动提交
                conn.setAutoCommit(false);
                // 比起st，pst会更好些
                pst = (PreparedStatement) conn.prepareStatement("");//准备执行语句
                // 外层循环，总提交事务次数
                for (int i = 1; i <= 10; i++) {
                    suffix = new StringBuffer();
                    // 第j次提交步长
                    for (int j = 1; j <= 10; j++) {
                        // 构建SQL后缀
                        temp = RandomValue.getCardType();
                        String time = format.format(RandomValue.getRandomDate("2009-07-01","2019-07-01"));
                        System.out.println(time);
                        id += 1;
                        suffix.append("('"+ RandomValue.getCardIdBuType(temp).toString()+"',"+temp +","+(r.nextInt(995)+5)+",'"+time+"',"+0.0+","+4+"),");
                    }
                    // 构建完整SQL
                    String sql = sqlPrefix + suffix.substring(0, suffix.length() - 1);
                    // 添加执行SQL
                    pst.addBatch(sql);
                    // 执行操作
                    pst.executeBatch();
                    // 提交事务
                    conn.commit();
                    // 清空上一次添加的数据
                    suffix = new StringBuffer();
                }
                endTime = new Date().getTime();//开始计时
            }else {
                System.out.println("数据库连接失败");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("com.mysql.jdbc.Driver驱动类没有找到");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库地址错误");
        }finally {//释放资源
            System.out.println("插入成功，所有时间："+ (endTime-beginTime));
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void insertCardDetail()  {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Date start =  format.parse("2009-07-01 00:00:00");
//        Date end =  format.parse("2019-07-01 00:00:00");
//        for (Long i = start.getTime(); i < end.getTime();) {
//        List<String> listDate ;
//        listDate  = handleCirculationDate("2019-01-01","2009-01-01");
//        Iterator<String> iterator  = listDate.iterator() ;
//
//        //遍历日期
//        while(iterator.hasNext()) {
//            //1.获取这一天的日期date，插入card表，不同城市创建不同张的初始卡，赋值
//            String date = iterator.next();
//            //2.插入carddetail，先获取card表这一天的date的卡列表，给每张卡转账まがせろ
//            //3.将carddetail的转账情况同步到card表
//            //4.metal表赋值今天日期date的数据
//            //5。插入loan表
//            //5.更新bill表，包括存储数、贷款数、中间业务
//
//            //获得当前时间
//             //遍历五级
//             int rowlength = BANKCITY_LEV.length;
//             //城市级别
//             int bank_lev = 0;
//             int bankid = 0;
//             //拿到城市的级别
//             for (int i = 0; i < rowlength; i++) {
//                 for (int j = 0; j < BANKCITY_LEV[i].length; j++) {
//                     bankid = BANKCITY_LEV[i][j];
//                     bank_lev = i;
//                     insertCardByDateAndBankLev(date,bankid,bank_lev);
//             }
//             }
//
//        }
//
        insertCardByDateAndBankLev();
    }

    //String date,int bankid,int bank_lev
    public void insertCardByDateAndBankLev(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int temp = 0;
        BigDecimal bill = new BigDecimal("0");
        BigDecimal billtemp = null;
        HashMap<Integer,BigDecimal> sum = new HashMap<>();
        try {
            Class.forName(driver);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);
            if(conn != null) {
                System.out.println("获取连接成功");
                beginTime = new Date().getTime();//开始计时
                //插入card表的insert语句
                String sqlPrefix = "INSERT INTO card(cardId, cardType, belongAccountId, createTime, serviceCharge,bankId, cardMoney,lastestTime) VALUES    ";
                //插入cardtradetail的insert语句
                String sqlPrefix2 = "  INSERT INTO cardtrandetail(cardId, createTime, tranMoney, tranType, serviceCharge)  VALUES   ";
                //插入bill的insert语句
                String sqlPrefix3 = "  INSERT INTO bill(bankId, day, business, money)  VALUES   ";
                // 保存sql后缀
                StringBuffer suffix = new StringBuffer();//插入card表的insert语句
                StringBuffer suffix2 = new StringBuffer();//插入cardtradetail的insert语句
                StringBuffer suffix3 = new StringBuffer();//插入bill表的insert语句
                // 设置事务为非自动提交
                conn.setAutoCommit(false);
                // 比起st，pst会更好些
                pst = (PreparedStatement) conn.prepareStatement("");//准备执行语句
                // 外层循环，总提交事务次
                suffix = new StringBuffer();
                suffix2 = new StringBuffer();
                suffix3 = new StringBuffer();


                /**
                 * for
                  */
                Date start =  format.parse("2009-07-01 00:00:00");
                Date end =  format.parse("2019-07-01 00:00:00");

                for(Long m = start.getTime(); m<end.getTime(); m +=604800000) {


//                List<String> listDate ;
//                listDate  = handleCirculationDate("2019-01-01","2009-01-01");
//                Iterator<String> iterator  = listDate.iterator() ;

                //遍历日期
//                while(iterator.hasNext()) {
                    //1.获取这一天的日期date，插入card表，不同城市创建不同张的初始卡，赋值
//                    String date = iterator.next();
                    //2.插入carddetail，先获取card表这一天的date的卡列表，给每张卡转账まがせろ
                    //3.将carddetail的转账情况同步到card表
                    //4.metal表赋值今天日期date的数据
                    //5。插入loan表
                    //5.更新bill表，包括存储数、贷款数、中间业务

                    //获得当前时间
                    //遍历五级
                    int rowlength = BANKCITY_LEV.length;
                    //城市级别
                    int bank_lev = 0;
                    int bankid = 0;
                    //遍历每天
                    for (Long l = m; l < m + 604800000; l += 86400000) {
                        //拿到城市的级别，并遍历城市
                        for (int i = 0; i < rowlength; i++) {
                            for (int j = 0; j < BANKCITY_LEV[i].length; j++) {
                                bankid = BANKCITY_LEV[i][j];
                                bank_lev = i;
                                temp =0;

                                //判断要建立多少张卡，根据bank_lev
                                int cardnumber = judgecard(bank_lev);
                                int randCard = r.nextInt(cardnumber)+cardnumber;
                                // 建卡，转账
                                for (int k = 1; k <= randCard; k++) {
                                    // 构建SQL后
                                    //每次随机一个时间
                                    Date d = new Date(r.nextInt(86400000) + l);
                                    String date = format.format(d);

                                    //对于cardid，新建一张卡，并且这张卡在当天转账
                                    String cardid = RandomValue.getCardIdBuType(0).toString();
                                    int cardMoney = (r.nextInt(50) + 1) * 10000;
                                    int tranmoney = (r.nextInt(99) + 1) * 100;//转账金额
                                    cardMoney = cardMoney - tranmoney;
                                    System.out.println(cardMoney);
//                                    temp +=cardMoney;
                                    String cardMoneyStr = ""+cardMoney;

                                    suffix.append("('" + cardid + "'," + 0 + "," + 1 + ",'" + date + "'," + 10.0 + "," + bankid + "," + cardMoney + ",'" + date + "'),");
                                    suffix2.append("('" + cardid + "','" + date + "'," + tranmoney + "," + 0 + "," + 0.001 * tranmoney + "),");

                                    BigDecimal value = new BigDecimal(cardMoneyStr);
                                    //取到目前为止的cardMoney
                                    if(sum.containsKey(bankid)){
                                        billtemp = value.add(sum.get(bankid));
                                        sum.put(bankid,billtemp);
//                                        if(bankid == 3 ) System.out.println(sum.get(bankid));
                                    }else {
                                        sum.put(bankid,value);
                                    }
                                }
                                Date billdate = new Date(l);
                                String billDate = format.format(billdate);
                                //更新bill
                                suffix3.append("("+bankid +",'"+billDate+"','" + "cardMoney"+ "','" + sum.get(bankid) +"'),");
                            }
                        }
                        System.out.println(sum.get(3));
                    }
                    System.out.println(ccc++);

                    // 构建完整SQL
                    String sql = sqlPrefix + suffix.substring(0, suffix.length() - 1);
                    String sql2 = sqlPrefix2 + suffix2.substring(0, suffix2.length() - 1);
                    String sql3 = sqlPrefix3 + suffix3.substring(0, suffix3.length() - 1);
//                    System.out.println(sql);
//                    System.out.println(sql2);
//                    System.out.println(sql3);
                    // 添加执行SQL
                    pst.addBatch(sql);
                    pst.addBatch(sql2);
                    pst.addBatch(sql3);
                    // 执行操作
                    pst.executeBatch();
                    // 提交事务
                    conn.commit();
                    // 清空上一次添加的数据
                    suffix = new StringBuffer();
                    suffix2 = new StringBuffer();
                    suffix3 = new StringBuffer();
                }
                endTime = new Date().getTime();//开始计时
            }else {
                System.out.println("数据库连接失败");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("com.mysql.jdbc.Driver驱动类没有找到");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库地址错误");
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {//释放资源
            System.out.println("插入成功，所有时间："+ (endTime-beginTime));
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int judgecard(int bank_lev){
        switch (bank_lev){
            case 0:return CARDNUMBER_LEV1;
            case 1:return CARDNUMBER_LEV2;
            case 2:return CARDNUMBER_LEV3;
            case 3:return CARDNUMBER_LEV4;
            case 4:return CARDNUMBER_LEV5;
            default:return 0;
        }
    }

    public int judgemetal(int bank_lev){
        switch (bank_lev){
            case 0:return METALNUMBER_LEV1;
            case 1:return METALNUMBER_LEV2;
            case 2:return METALNUMBER_LEV3;
            case 3:return METALNUMBER_LEV4;
            case 4:return METALNUMBER_LEV5;
            default:return 0;
        }
    }

    //插入贵金属表
    public void insertMetal(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        HashMap<Integer,BigDecimal> metalsum = new HashMap<>();//key:bankid
        try {
            int rowlength = BANKCITY_LEV.length;
            int bankid;
            int bank_lev;
            int metalnumber;
            Class.forName(driver);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);
            if(conn != null) {
                System.out.println("获取连接成功");
                beginTime = new Date().getTime();//开始计时
                //插入metal表的insert语句
                String sqlPrefix = "INSERT INTO  metal(metalType, money, serviceCharge, createTime, bankid) VALUES    ";
                // 插入bill表的insert语句
                String sqlPrefix2 = "INSERT INTO  bill(bankId,day,business,money) VALUES    ";

                StringBuffer suffix = new StringBuffer();//插入metal表的insert语句
                StringBuffer suffix2 = new StringBuffer();//插入bill表的insert语句
                // 设置事务为非自动提交
                conn.setAutoCommit(false);
                // 比起st，pst会更好些
                pst = (PreparedStatement) conn.prepareStatement("");//准备执行语句
                // 外层循环，总提交事务次
                suffix = new StringBuffer();

                Date start =  format.parse("2009-07-01 00:00:00");
                Date end =  format.parse("2019-07-01 00:00:00");

                for(Long m = start.getTime(); m<end.getTime(); m +=604800000) {
                    for (int i = 0; i < rowlength; i++){
                        for (int j = 0; j < BANKCITY_LEV[i].length; j++){
                            bankid = BANKCITY_LEV[i][j];
                            bank_lev = i;
                            metalnumber = judgemetal(bank_lev);
                            for (Long l = m; l < m + 604800000; l += 86400000) {
                                int metaltemp = 0;
                                for (int k = 1; k <=metalnumber ; k++) {//几次

                                    // 构建SQL后
                                    //每次随机一个时间
                                    Date d = new Date(r.nextInt(86400000) + l);
                                    String date = format.format(d);
                                    int metalMoney = (r.nextInt(99) + 1) * 10000;
                                    metaltemp+=metalMoney;
                                    suffix.append("('" + "gold"+"'," + metalMoney+"," +metalMoney*0.0042+",'"+ date+"',"+bankid+"),");

                                }
//                                Date dl = new Date(l);
//                                String datel = format.format(dl);
//                                suffix2.append("(" +bankid+ ",'"+ datel+"'"+ ""+"'"+"'),");

                                }
                    // 构建完整SQL
                    String sql = sqlPrefix + suffix.substring(0, suffix.length() - 1);
                    // 添加执行SQL
                    pst.addBatch(sql);
                    // 执行操作
                    pst.executeBatch();
                    // 提交事务
                    conn.commit();
                    // 清空上一次添加的数据
                    suffix = new StringBuffer();
                        }
                    }
                        }
                endTime = new Date().getTime();//开始计时
        }else {
            System.out.println("数据库连接失败");
        }
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
        System.out.println("com.mysql.jdbc.Driver驱动类没有找到");
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("数据库地址错误");
    } catch (ParseException e) {
        e.printStackTrace();
    } finally {//释放资源
        System.out.println("插入成功，所有时间："+ (endTime-beginTime));
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(pst != null) {
            try {
                pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
        }

    public void insertloan(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        HashMap<Integer,BigDecimal> metalsum = new HashMap<>();//key:bankid
        try {
            int rowlength = BANKCITY_LEV.length;
            int bankid;
            int bank_lev;
            int metalnumber;
            Class.forName(driver);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);
            if(conn != null) {
                System.out.println("获取连接成功");
                beginTime = new Date().getTime();//开始计时
                //插入loan表的insert语句
                String sqlPrefix = "INSERT INTO  loan(accountId, loanMoney, latestMoney, loanType, creatTime, serviceCharge, bankId, flag) VALUES    ";
                // 插入bill表的insert语句
                String sqlPrefix2 = "INSERT INTO  bill(bankId,day,business,money) VALUES    ";

                StringBuffer suffix = new StringBuffer();//插入metal表的insert语句
                StringBuffer suffix2 = new StringBuffer();//插入bill表的insert语句
                // 设置事务为非自动提交
                conn.setAutoCommit(false);
                // 比起st，pst会更好些
                pst = (PreparedStatement) conn.prepareStatement("");//准备执行语句
                // 外层循环，总提交事务次
                suffix = new StringBuffer();

                Date start =  format.parse("2009-07-01 00:00:00");
                Date end =  format.parse("2019-07-01 00:00:00");

                for(Long m = start.getTime(); m<end.getTime(); m +=604800000) {
                    for (int i = 0; i < rowlength; i++){
                        for (int j = 0; j < BANKCITY_LEV[i].length; j++){
                            bankid = BANKCITY_LEV[i][j];
                            bank_lev = i;
                            metalnumber = judgemetal(bank_lev);
                            for (Long l = m; l < m + 604800000; l += 86400000) {
                                int metaltemp = 0;
                                for (int k = 1; k <=metalnumber ; k++) {//几次

                                    // 构建SQL后
                                    //每次随机一个时间
                                    Date d = new Date(r.nextInt(86400000) + l);
                                    String date = format.format(d);
                                    int metalMoney = (r.nextInt(99) + 1) * 10000;
                                    metaltemp+=metalMoney;
                                    suffix.append("('" + "gold"+"'," + metalMoney+"," +metalMoney*0.0042+",'"+ date+"',"+bankid+"),");

                                }
//                                Date dl = new Date(l);
//                                String datel = format.format(dl);
//                                suffix2.append("(" +bankid+ ",'"+ datel+"'"+ ""+"'"+"'),");

                            }
                            // 构建完整SQL
                            String sql = sqlPrefix + suffix.substring(0, suffix.length() - 1);
                            // 添加执行SQL
                            pst.addBatch(sql);
                            // 执行操作
                            pst.executeBatch();
                            // 提交事务
                            conn.commit();
                            // 清空上一次添加的数据
                            suffix = new StringBuffer();
                        }
                    }
                }
                endTime = new Date().getTime();//开始计时
            }else {
                System.out.println("数据库连接失败");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("com.mysql.jdbc.Driver驱动类没有找到");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库地址错误");
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {//释放资源
            System.out.println("插入成功，所有时间："+ (endTime-beginTime));
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<String> handleCirculationDate(String today,String passday){
        List<String> listDate = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date startDate = dateFormat.parse(passday);
            Date endDate = dateFormat.parse(today);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            while(calendar.getTime().before(endDate)){
//                System.out.println(dateFormat.format(calendar.getTime()));
                listDate.add(dateFormat.format(calendar.getTime()));
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            return listDate;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }



}
