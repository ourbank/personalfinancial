package com.icbc.index.util;


import com.icbc.index.model.CardData;
import com.icbc.index.model.Msql;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import javax.smartcardio.Card;
import java.util.*;

public class JSONParseUtil {

    /**
     * 从百度分词API中获取得到关键分词，组装成为特定的sql对象
     *
     * @param str List<Msql>
     * @return
     */
    public static String getMsql(String str) {
        JSONObject json = new JSONObject(str);
        List<String> timeList = new ArrayList<>();
        List<String> companyList = new ArrayList<>();
        List<String> businessList = new ArrayList<>();
        String operate = "";
        JSONArray out = json.getJSONArray("items");
        Iterator jsonIter = out.iterator();
        while (jsonIter.hasNext()) {
            JSONObject jsonObject = (JSONObject) jsonIter.next();
            // 介词不进行记录
            if (!jsonObject.getString("pos").equals("u")) {
                if (operate.equals("")) {
                    if (jsonObject.getString("item").equals("查询")) {
                        operate = "查询";
                    } else if (jsonObject.getString("item").equals("对比")) {
                        operate = "对比";
                    } else {
                        operate = "统计";
                    }
                }
                // 记录时间
                if (jsonObject.getString("ne").equals("TIME")) {
                    timeList.add(jsonObject.getString("item"));
                } else if (jsonObject.getString("item").contains("号")) {
                    timeList.add(jsonObject.getString("item"));
                }
                // 记录地点
                else if (jsonObject.getString("ne").equals("ORG")) {
                    companyList.add(jsonObject.getString("item"));
                } else if (jsonObject.getString("ne").equals("LOC")) {
                    companyList.add(jsonObject.getString("item") + "分行");
                }
                // 记录业务
                else {
                    businessList.add(jsonObject.getString("item"));
                }
            }
        }
        //
        Msql msql = toMsql(operate, timeProcess(timeList), companyList, businessProcess(businessList));
        String select = tranMsql(msql);
        // 测试用
        Map map = new HashMap();
        map.put("操作", operate);
        map.put("时间", timeProcess(timeList));
        map.put("公司", companyList);
        map.put("业务", businessProcess(businessList));
        map.put("sql语言", select);
        JSONObject outJson = new JSONObject(map);
        return outJson.toString();
        // return msqlList;
    }


    /**
     * 通过四大要素构建sql查询集合
     *
     * @param operate      数据库操作 查询，对比 或者 统计
     * @param timeList     时间集合，用于获取查询数据的时间范围
     * @param companyList  公司集合，用于获取查询的银行
     * @param businessList 业务集合，用于查询业务类别
     * @return
     */
    private static Msql toMsql(String operate, List<String> timeList, List<String> companyList, List<String> businessList) {
        Date fromDate = null;
        Date toDate = null;
        String period = null;
        if (timeList.size() > 1) {
            if (timeList.get(0).contains("号")) {
                period = "daytoday";
            } else if (timeList.get(0).contains("月")) {
                period = "monthtomonth";
            } else {
                period = "yeartoyear";
            }
            fromDate = TimeUtil.strToDateLong(timeList.get(0));
            toDate = TimeUtil.strToDateLong(timeList.get(timeList.size() - 1));
        } else {
            if (timeList.get(0).contains("号")) {
                period = "day";
            } else if (timeList.get(0).contains("月")) {
                period = "daysinmonth";
            } else {
                period = "monthsinyear";
            }
            fromDate = TimeUtil.strToDateLong(timeList.get(0));
        }
        Msql msql = new Msql(fromDate, toDate, companyList, businessList, operate, period);
        return msql;
    }

    /**
     * 将SQL类别翻译成为查询语句
     *
     * @return
     */
    private static String tranMsql(Msql msql) {
        String sql = "";
        switch (msql.getOperate()) {
            case "查询":
                sql = tranSelectMsql(msql);
                break;
            case "统计":
                //sql = tranCountMsql(msql);
                break;
            case "对比":
                //sql = tranCompareMsql(msql);
                break;
        }
        return sql;
    }

    /**
     * 处理查询请求
     *
     * @param msql
     */
    private static String tranSelectMsql(Msql msql) {
        String sql = "";
        String business = msql.getBusiness().get(0);
        switch (msql.getPeriod()) {
            // 查询具体某一天的情况
            // 这部分代码后期转移到dao层中，在这里测试用！！！
            case "day":
                // 只支持同时一种业务的查询
                // 样例语句：select count(card.id) as newCardNum, bankname as bankName,DATE_FORMAT(createTime,'%Y-%m-%d') as day from bank inner join card on bank.id = card.bankId
                //where DATE_FORMAT(createTime,'%Y-%m-%d') = '2019-07-06' GROUP BY bankName
                sql = sql + "select count(card.id),bankname,date_format(createtime,%Y-%m-%d) as day from bank inner join card on bank.id = card.bankId " +
                        "where date_format(createtime,%Y-%m-%d) = '" + TimeUtil.dateToDayStr(msql.getFromData()) + "' group by bankname having ";
                for (String company : msql.getCompany()) {
                    sql = sql + " bankname = '" + company + "' or ";
                }
                sql = sql.substring(0, sql.lastIndexOf("or"));
                //System.out.println(sql);
                break;
            //查询某个月中每一天的情况
            case "daysinmonth":
                //样例语句：select count(card.id) as newCardNum, bankname as bankName,DATE_FORMAT(createTime,'%Y-%m-%d') as day from bank inner join card on bank.id = card.bankId
                //where DATE_FORMAT(createTime,'%Y-%m') = '2019-07' GROUP BY bankName,day
                sql = sql + "select count(card.id) as newCardNum, bankname as bankName,DATE_FORMAT(createTime,'%Y-%m-%d') as day from bank inner join card on bank.id = card.bankId " +
                        "where date_format(createtime,%Y-%m) = '" + TimeUtil.dateToMonthStr(msql.getFromData()) + "' group by bankname,day having bankname = ";
                for (String company : msql.getCompany()) {
                    sql = sql + " bankname = '" + company + "' or ";
                }
                sql = sql.substring(0, sql.lastIndexOf("or"));
                //System.out.println(sql);
                break;
            //查询某一年中每个月的情况
            case "monthsinyear":
                //样例语句：select count(card.id) as newCardNum, bankname as bankName,DATE_FORMAT(createTime,'%Y-%m') as month from bank inner join card on bank.id = card.bankId
                //where DATE_FORMAT(createTime,'%Y') = '2019' GROUP BY bankName,month
                sql = sql + "select count(card.id) as newCardNum, bankname as bankName,DATE_FORMAT(createTime,'%Y-%m') as month from bank inner join card on bank.id = card.bankId " +
                        "where date_format(createtime,%Y) = '" + TimeUtil.dateToYearStr(msql.getFromData()) + "' group by bankname,month having bankname = ";
                for (String company : msql.getCompany()) {
                    sql = sql + " bankname = '" + company + "' or ";
                }
                sql = sql.substring(0, sql.lastIndexOf("or"));
                //System.out.println(sql);
                break;
            // 查询以天为单位的范围
            case "daytoday":
                //样例语句：select count(card.id) as newCardNum, bankname as bankName,DATE_FORMAT(createTime,'%Y-%m-%d') as day from bank inner join card on bank.id = card.bankId
                //where createTime BETWEEN '2019-07-01' and '2019-07-07' GROUP BY bankName,day
                sql = sql + "select count(card.id) as newCardNum, bankname as bankName,DATE_FORMAT(createTime,'%Y-%m-%d') as day from bank inner join card on bank.id = card.bankId " +
                        "where createtime between '" + TimeUtil.dateToDayStr(msql.getFromData()) + "' and '" + TimeUtil.dateToDayStr(msql.getToData()) + "' group by bankname,day having bankname = ";
                for (String company : msql.getCompany()) {
                    sql = sql + " bankname = '" + company + "' or ";
                }
                sql = sql.substring(0, sql.lastIndexOf("or"));
                //System.out.println(sql);
                break;
            case "monthtomonth":
                //样例语句：select count(card.id) as newCardNum, bankname as bankName,DATE_FORMAT(createTime,'%Y-%m') as month from bank inner join card on bank.id = card.bankId
                //where createTime BETWEEN '2019-06-01' and '2019-08-01' GROUP BY bankName,month
                sql = sql + "select count(card.id) as newCardNum, bankname as bankName,DATE_FORMAT(createTime,'%Y-%m-%d') as day from bank inner join card on bank.id = card.bankId " +
                        "where createtime between '" + TimeUtil.dateToDayStr(msql.getFromData()) + "' and '" + TimeUtil.getNextMonnth(msql.getToData()) + "' group by bankname,day having bankname = ";
                for (String company : msql.getCompany()) {
                    sql = sql + " bankname = '" + company + "' or ";
                }
                sql = sql.substring(0, sql.lastIndexOf("or"));
                //System.out.println(sql);
                break;
            case "yeartoyear":
                //样例语句：select count(card.id) as newCardNum, bankname as bankName,DATE_FORMAT(createTime,'%Y') as year from bank inner join card on bank.id = card.bankId
                //where createTime BETWEEN '2018-01-01' and '2020-01-01' GROUP BY bankName,year
                sql = sql + "select count(card.id) as newCardNum, bankname as bankName,DATE_FORMAT(createTime,'%Y') as year from bank inner join card on bank.id = card.bankId " +
                        "where createtime between '" + TimeUtil.dateToDayStr(msql.getFromData()) + "' and '" + TimeUtil.getNextYear(msql.getToData()) + "' group by bankname,day having bankname = ";
                for (String company : msql.getCompany()) {
                    sql = sql + " bankname = '" + company + "' or ";
                }
                sql = sql.substring(0, sql.lastIndexOf("or"));
                //System.out.println(sql);
                break;
        }
        return sql;
    }

    /**
     * 业务处理，过滤一下识别到的模糊词语
     *
     * @param businessList
     * @return
     */
    private static List<String> businessProcess(List<String> businessList) {
        List<String> out = new ArrayList<>();
        for (String business : businessList) {
            if (business.contains("中间")) {
                out.add("中间业务收入");
            } else if (business.contains("开卡") || business.contains("开户")) {
                out.add("开卡数");
            } else if (business.contains("贷款")) {
                out.add("贷款数");
            } else if (business.contains("存款")) {
                out.add("存款数");
            }
        }
        return out;
    }

    /**
     * 时间处理，进行一些补足操作或者默认操作
     *
     * @param timeList
     * @return
     */
    private static List<String> timeProcess(List<String> timeList) {
        List<String> out = new ArrayList<>();
        if (timeList == null || timeList.isEmpty()) {
            // 没有输入时间，默认查询本年
            out.add(TimeUtil.getYear());
            return out;
        }
        for (String time : timeList) {
            // 时间为 1号到2号的情况，默认补足查询当日的年月
            if (time.contains("号") && !time.contains("月") && !time.contains("年")) {
                time = TimeUtil.getYearMonth() + time;
                out.add(time);
            }
            // 时间为 8月 的情况，默认补足查询当日的年
            if (!time.contains("号") && time.contains("月") && !time.contains("年")) {
                time = TimeUtil.getYear() + time;
                out.add(time);
            }
            // 时间为 8月5号 的情况，默认补足查询当日的年
            if (time.contains("号") && time.contains("月") && !time.contains("年")) {
                time = TimeUtil.getYear() + time;
                out.add(time);
            }
        }
//        for(String time : out){
//            System.out.println(time);
//        }
        return out;
    }


    /**
     * 获取一个全0的Map数据体，年月日嵌套
     * {
     * data:[{ time:'2019年'
     * data:[{
     * time:'1月'
     * data:[0,0,0,0...] 31天的数据
     * },
     * {},{}..12个月
     * ]
     * },
     * {},{}
     * ]
     * bussiness: '开卡'
     * bankname: '广州分行'
     * }
     *
     * @param startTime
     * @param endTime
     * @param bankName
     * @param buss
     * @return
     */
    public static Map getDefaultMap(String startTime, String endTime, List<String> bankName, List<String> buss) {
        Map outMap = new HashMap();
        int startYear = Integer.parseInt(startTime.substring(0, 4));
        int endYear = Integer.parseInt(endTime.substring(0, 4));
        int startMon = Integer.parseInt(startTime.substring(5));
        int endMon = Integer.parseInt(endTime.substring(5));
        int i_endMon, i_startMon;
        // 不同业务
        for (int b = 0; b < buss.size(); b++) {
            outMap.put("bussiness", buss.get(b));
            List<Map> citys = new ArrayList<>();
            //不同城市
            for (int z = 0; z < bankName.size(); z++) {
                Map citymap = new HashMap();
                citymap.put("bankname", bankName.get(z));
                List<Map> years = new ArrayList<>();
                for (int i = startYear; i <= endYear; i++) {
                    Map yearmap = new HashMap();
                    yearmap.put("time", i + "年");
                    List<Map> months = new ArrayList<>();
                    //判断需要迭代的月份
                    if (i == startYear) {
                        i_startMon = startMon;
                        i_endMon = 12;
                    } else if (i == endYear) {
                        i_startMon = 1;
                        i_endMon = endMon;
                    } else {
                        i_startMon = 1;
                        i_endMon = 12;
                    }
                    for (int j = i_startMon; j <= i_endMon; j++) {
                        Map monthmap = new HashMap();
                        monthmap.put("time", j + "月");
                        List<Integer> days = new ArrayList<>();
                        for (int k = 1; k <= TimeUtil.getDayOfMonth(i, j); k++) {
                            days.add(0);
                        }
                        monthmap.put("days", days);
                        months.add(monthmap);
                    }
                    yearmap.put("months", months);
                    years.add(yearmap);
                }
                citymap.put("years", years);
                citys.add(citymap);
            }
            outMap.put("citys", citys);
        }
        return outMap;
    }


    public static String getJSONMap(String startTime, String endTime, List<String> bankName, List<String> buss, Map<String, List<CardData>> inputData) {
        //CardData{cardNum=22, bankName='广州分行', time=Fri Feb 02 08:00:00 CST 2018}
        Map outMap = getDefaultMap(startTime, endTime, bankName, buss);
        Map.Entry<String, List<CardData>> entry;
        Iterator<Map.Entry<String, List<CardData>>> iterator = inputData.entrySet().iterator();
        int startYear = Integer.parseInt(startTime.substring(0, 4));
        for (int b = 0; b < buss.size(); b++) {
            while (iterator.hasNext()) {
                entry = iterator.next();
                List<CardData> list = entry.getValue();
                int year, month, day;
                for (int i = 0; i < list.size(); i++) {
                    CardData s = list.get(i);
                    year = Integer.parseInt(TimeUtil.dateToFormatStr(s.getTime(), "yyyy"));
                    month = Integer.parseInt(TimeUtil.dateToFormatStr(s.getTime(), "MM"));
                    day = Integer.parseInt(TimeUtil.dateToFormatStr(s.getTime(), "dd"));

                    ((ArrayList<Integer>)
                            ((ArrayList<Map>)
                                    ((ArrayList<Map>) outMap.get("years")).get(year - startYear)
                                            .get("months")).get(month - 1)
                                    .get("days")).set(day - 1, s.getCardNum());
                }
            }
        }


//        int year,month,day;
//
//            List<>
//            for (int i = 0; i < entry.getValue().size(); i++) {
//                year = Integer.parseInt(TimeUtil.dateToFormatStr(entry.getValue().get(i).getTime(),"yyyy"));
//                month = Integer.parseInt(TimeUtil.dateToFormatStr(entry.getValue().get(i).getTime(),"MM"));
//                day = Integer.parseInt(TimeUtil.dateToFormatStr(entry.getValue().get(i).getTime(),"dd"));
//
//            }
//            outMap.put(entry.getValue(),list)
//        }

//        outMap.put("bussiness",buss);
//
//
//
//
//        outMap.put("bankname",bankName);
//        List<Map> years = new ArrayList<>();
//        for(int i = startYear;i <= endYear;i++){
//            Map yearmap = new HashMap(); //生成每一年的map
//            yearmap.put("time",i+"年");
//            List<Map> months = new ArrayList<>();
//            for(int j = 1; j <= 12 ; j++){
//                Map monthmap = new HashMap();
//                monthmap.put("time",j+"月");
//                List<Integer> days = new ArrayList<>();
//                for (int k = 1; k <= TimeUtil.getDayOfMonth(i,j);k++){
//                    days.add(0);
//                }
//                monthmap.put("days",days);
//                months.add(monthmap);
//            }
//            yearmap.put("months",months);
//            years.add(yearmap);
//        }
//        outMap.put("years",years);
        return outMap.toString();
    }
}
