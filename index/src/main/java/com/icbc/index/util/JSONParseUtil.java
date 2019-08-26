package com.icbc.index.util;


import com.alibaba.fastjson.JSON;
import com.icbc.index.model.BusinessSaveData;
import com.icbc.index.model.CardData;
import com.icbc.index.model.CoreInQuerySQL;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class JSONParseUtil {

    private static Logger logger= LoggerFactory.getLogger(JSONParseUtil.class);

    /**
     * 从百度分词API中获取得到关键分词，组装成为特定的sql对象
     *
     * @param str List<CoreInQuerySQL>
     * @return
     */
    public static String getMsql(String str) {
        JSONObject json = JSONObject.parseObject(str);
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
        CoreInQuerySQL coreInQuerySQL = toMsql(operate, timeProcess(timeList), companyList, businessProcess(businessList));
        String select = tranMsql(coreInQuerySQL);
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
    private static CoreInQuerySQL toMsql(String operate, List<String> timeList, List<String> companyList, List<String> businessList) {
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
        CoreInQuerySQL coreInQuerySQL = new CoreInQuerySQL(fromDate, toDate, companyList, businessList, operate, period);
        return coreInQuerySQL;
    }

    /**
     * 将SQL类别翻译成为查询语句
     *
     * @return
     */
    private static String tranMsql(CoreInQuerySQL coreInQuerySQL) {
        String sql = "";
        switch (coreInQuerySQL.getOperate()) {
            case "查询":
                sql = tranSelectMsql(coreInQuerySQL);
                break;
            case "统计":
                //sql = tranCountMsql(coreInQuerySQL);
                break;
            case "对比":
                //sql = tranCompareMsql(coreInQuerySQL);
                break;
        }
        return sql;
    }

    /**
     * 处理查询请求
     *
     * @param coreInQuerySQL
     */
    private static String tranSelectMsql(CoreInQuerySQL coreInQuerySQL) {
        String sql = "";
        String business = coreInQuerySQL.getBusiness().get(0);
        switch (coreInQuerySQL.getPeriod()) {
            // 查询具体某一天的情况
            // 这部分代码后期转移到dao层中，在这里测试用！！！
            case "day":
                // 只支持同时一种业务的查询
                // 样例语句：select count(card.id) as newCardNum, bankname as bankName,DATE_FORMAT(createTime,'%Y-%m-%d') as day from bank inner join card on bank.id = card.bankId
                //where DATE_FORMAT(createTime,'%Y-%m-%d') = '2019-07-06' GROUP BY bankName
                sql = sql + "select count(card.id),bankname,date_format(createtime,%Y-%m-%d) as day from bank inner join card on bank.id = card.bankId " +
                        "where date_format(createtime,%Y-%m-%d) = '" + TimeUtil.dateToDayStr(coreInQuerySQL.getFromData()) + "' group by bankname having ";
                for (String company : coreInQuerySQL.getCompany()) {
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
                        "where date_format(createtime,%Y-%m) = '" + TimeUtil.dateToMonthStr(coreInQuerySQL.getFromData()) + "' group by bankname,day having bankname = ";
                for (String company : coreInQuerySQL.getCompany()) {
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
                        "where date_format(createtime,%Y) = '" + TimeUtil.dateToYearStr(coreInQuerySQL.getFromData()) + "' group by bankname,month having bankname = ";
                for (String company : coreInQuerySQL.getCompany()) {
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
                        "where createtime between '" + TimeUtil.dateToDayStr(coreInQuerySQL.getFromData()) + "' and '" + TimeUtil.dateToDayStr(coreInQuerySQL.getToData()) + "' group by bankname,day having bankname = ";
                for (String company : coreInQuerySQL.getCompany()) {
                    sql = sql + " bankname = '" + company + "' or ";
                }
                sql = sql.substring(0, sql.lastIndexOf("or"));
                //System.out.println(sql);
                break;
            case "monthtomonth":
                //样例语句：select count(card.id) as newCardNum, bankname as bankName,DATE_FORMAT(createTime,'%Y-%m') as month from bank inner join card on bank.id = card.bankId
                //where createTime BETWEEN '2019-06-01' and '2019-08-01' GROUP BY bankName,month
                sql = sql + "select count(card.id) as newCardNum, bankname as bankName,DATE_FORMAT(createTime,'%Y-%m-%d') as day from bank inner join card on bank.id = card.bankId " +
                        "where createtime between '" + TimeUtil.dateToDayStr(coreInQuerySQL.getFromData()) + "' and '" + TimeUtil.getNextMonnth(coreInQuerySQL.getToData()) + "' group by bankname,day having bankname = ";
                for (String company : coreInQuerySQL.getCompany()) {
                    sql = sql + " bankname = '" + company + "' or ";
                }
                sql = sql.substring(0, sql.lastIndexOf("or"));
                //System.out.println(sql);
                break;
            case "yeartoyear":
                //样例语句：select count(card.id) as newCardNum, bankname as bankName,DATE_FORMAT(createTime,'%Y') as year from bank inner join card on bank.id = card.bankId
                //where createTime BETWEEN '2018-01-01' and '2020-01-01' GROUP BY bankName,year
                sql = sql + "select count(card.id) as newCardNum, bankname as bankName,DATE_FORMAT(createTime,'%Y') as year from bank inner join card on bank.id = card.bankId " +
                        "where createtime between '" + TimeUtil.dateToDayStr(coreInQuerySQL.getFromData()) + "' and '" + TimeUtil.getNextYear(coreInQuerySQL.getToData()) + "' group by bankname,day having bankname = ";
                for (String company : coreInQuerySQL.getCompany()) {
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
        // 如果想查询具体某个月的
        //int startMon = Integer.parseInt(startTime.substring(5,7));
        //int endMon = Integer.parseInt(endTime.substring(5,7));

        // 查询所有月份的
        int startMon = 1;
        int endMon = 12;
        int i_endMon, i_startMon;
        // 不同业务
        for (int b = 0; b < buss.size(); b++) {
            outMap.put("bussiness", buss.get(b));
            List<Map> cities= new ArrayList<>();
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
                        monthmap.put("data", days);
                        months.add(monthmap);
                    }
                    yearmap.put("data", months);
                    years.add(yearmap);
                }
                citymap.put("data", years);
                cities.add(citymap);
            }
            outMap.put("citys", cities);
        }
        return outMap;
    }



    public static String getJSONMap(String startTime, String endTime, List<String> bankName, List<String> buss, Map<String, List<CardData>> inputData) {
        //CardData{cardNum=22, bankName='广州分行', time=Fri Feb 02 08:00:00 CST 2018}
        //
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
                    ((ArrayList<Double>)
                            ((ArrayList<Map>)
                                    ((ArrayList<Map>) outMap.get("years")).get(year - startYear)
                                            .get("months")).get(month - 1)
                                    .get("days")).set(day - 1, s.getCardNum());
                }
            }
        }

        return outMap.toString();
    }



    /**
     * 核心查询界面的查询接口，只支持单一业务，默认查询年的所有数据，不区分月份
     * @param startTime 开始时间 xxxx-xx-xx
     * @param endTime 结束 xxxx-xx-xx
     * @param bankName 银行列表
     * @param buss 单一业务
     * @param input 从数据库查得的数据，格式 [CardData{bankName,bussiness,time},....]
     * @return
     */
    public static String getSingleBusJson(String startTime, String endTime, List<String> bankName, String buss,List<CardData> input){
        List<Map> out = getDefaultMap(startTime,endTime,bankName,buss);
        int startYear = Integer.parseInt(startTime.substring(0, 4));
        // 如果想查询具体某个月的
        //int startMon = Integer.parseInt(startTime.substring(5,7));
        // 查询所有月份的
        int startMon = 1;
        int local_mon;
        int year,month,day;
        for (int i = 0; i < input.size(); i++) {
            CardData cardData = input.get(i);
            year = Integer.parseInt(TimeUtil.dateToFormatStr(cardData.getTime(), "yyyy"));
            month = Integer.parseInt(TimeUtil.dateToFormatStr(cardData.getTime(), "MM"));
            day = Integer.parseInt(TimeUtil.dateToFormatStr(cardData.getTime(), "dd"));
            //判断当前城市
            for (int j = 0; j < out.size(); j++) {
                if(out.get(j).get("bankname").equals(cardData.getBankName())){
                    if (year == startYear) {
                        local_mon = startMon;
                    } else {
                        local_mon = 1;
                    }
                    ((ArrayList<Double>)
                            ((ArrayList<Map>)
                                    ((ArrayList<Map>) out.get(j).get("data")).get(year - startYear)
                                            .get("data")).get(month - local_mon)
                                    .get("data")).set(day - 1, cardData.getCardNum());
                    break;
                }
            }
        }
        return JSON.toJSONString(out);
    }

    /**
     * 核心查询界面的查询接口,返回一个全零的初始化JSONArray（String格式）
     * @param startTime 开始时间 xxxx-xx-xx
     * @param endTime 结束时间 xxxx-xx-xx
     * @param bankName 银行列表 支持多银行
     * @param buss 业务 只支持单一业务
     * @return
     */
    public static List<Map> getDefaultMap(String startTime, String endTime, List<String> bankName, String buss) {
        List<Map> out = new ArrayList<>();
        int startYear = Integer.parseInt(startTime.substring(0, 4));
        int endYear = Integer.parseInt(endTime.substring(0, 4));
        // 如果想查询具体某个月的
        //int startMon = Integer.parseInt(startTime.substring(5,7));
        //int endMon = Integer.parseInt(endTime.substring(5,7));

        // 查询所有月份的
        int startMon = 1;
        int endMon = 12;
        int i_endMon, i_startMon;
        //不同城市
        for (int z = 0; z < bankName.size(); z++) {
            Map citymap = new HashMap();
            citymap.put("bankname", bankName.get(z));
            citymap.put("business", buss);
            citymap.put("starttime", startTime);
            citymap.put("endtime", endTime);
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
                    monthmap.put("data", days);
                    months.add(monthmap);
                }
                yearmap.put("data", months);
                years.add(yearmap);
            }
            citymap.put("data", years);
            out.add(citymap);
        }

        return out;
    }

    /**多业务多城市查询返回前端table的JSON格式
     * {
     *     "tablecolData": [
     *         {
     *             "colName": "日期",
     *             "colItem": "tableData"
     *         },
     *         {
     *             "colName": "广州/存款数",
     *             "colItem": "广州存款数"
     *         },
     *         {
     *             "colName": "广州/开卡数",
     *             "colItem": "广州开卡数"
     *         },
     *         {
     *             "colName": "深圳/存款数",
     *             "colItem": "深圳存款数"
     *         },
     *         {
     *             "colName": "深圳/开卡数",
     *             "colItem": "深圳开卡数"
     *         }
     *     ],
     *     "tableData": [
     *         {
     *             "广州存款数": 27,
     *             "深圳存款数": 35,
     *             "深圳开卡数": 25767570100,
     *             "广州开卡数": 25655509300,
     *             "tableDate": "2019-01-01"
     *         },
     *         {
     *             "广州存款数": 36,
     *             "深圳存款数": 38,
     *             "深圳开卡数": 25776485300,
     *             "广州开卡数": 25663916700,
     *             "tableDate": "2019-01-02"
     *         },
     *         {
     *             "广州存款数": 34,
     *             "深圳存款数": 37,
     *             "深圳开卡数": 25785846900,
     *             "广州开卡数": 25671529000,
     *             "tableDate": "2019-01-03"
     *         }
     *     ]
     * }
     * @author zhenjin
     * @param bankSet
     * @param businessSet
     * @param startTime
     * @param endTime
     * @param input
     * @return
     */
    public static JSONObject getSaveJson(TreeSet<Integer> bankSet, TreeSet<String> businessSet,
                                     String startTime, String endTime,
                                     List<BusinessSaveData> input){
        Map<String,Object> outmap = new HashMap<>();
        List<Map<String,String>> tablecolData = new ArrayList<>();
        List<Map<String,Object>> tableData = new ArrayList<>();
        int index = 0;
        int size = bankSet.size()+businessSet.size();
        //1.添加tablecolData的colItem和colName
        Map<String,String> tablecolMap = new HashMap<>();
        tablecolMap.put("colItem", "tableData");
        tablecolMap.put("colName","日期");
        tablecolData.add(tablecolMap);
        for(int ba : bankSet){
            for(String bu :businessSet){
                tablecolMap = new HashMap<>();
                tablecolMap.put("colName", BankIdConstant.getAddrByBankId(ba) +"/" + TableNameConstant.getBusinessNameByBill(bu));
                tablecolMap.put("colItem", BankIdConstant.getAddrByBankId(ba) + TableNameConstant.getBusinessNameByBill(bu));
                tablecolData.add(tablecolMap);
            }
        }

        try {

            //2.添加tableData 的tableDate 和其他数据
            Calendar calendar = Calendar.getInstance();
            List<String> everydays = AccountDate.getEveryday(startTime,endTime);
            for (String date: everydays) {
                HashMap<String, Object> tableDataMap = new HashMap<>();
                tableDataMap.put("tableData",date);
                for(String busine : businessSet){
                    for(int bank : bankSet){
                        if(index >= input.size()) continue;
                        tableDataMap.put(BankIdConstant.getAddrByBankId(bank)+TableNameConstant.getBusinessNameByBill(busine), input.get(index).getNum());
                        index++;
                    }
                }
                tableData.add(tableDataMap);
            }
        }catch (IndexOutOfBoundsException e){
            System.out.println("日期错误");
        }

        outmap.put("tablecolData",tablecolData);
        outmap.put("tableData",tableData);

        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(outmap));
        return jsonObject;
    }
}
