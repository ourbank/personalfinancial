package com.icbc.index.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icbc.index.mapper.CardDao;
import com.icbc.index.mapper.CoreInquiry;
import com.icbc.index.model.CardData;
import com.icbc.index.model.CoreInQuerySQL;
import com.icbc.index.model.CoreInquiryParam;
import com.icbc.index.util.JSONParseUtil;
import com.icbc.index.util.TimeUtil;
import com.mysql.cj.xdevapi.JsonArray;
import com.mysql.cj.xdevapi.JsonValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


@Service
public class PythonService {


    @Resource
    PythonInvoke pythonInvoke;

    Logger logger = LoggerFactory.getLogger(PythonService.class);

    //用户服务类
    @Autowired
    ManagerService managerService;

    @Resource
    CoreInquiry coreInquiry;
    private static Map<String, Integer> map = new HashMap<>();

    private Calendar calendar = Calendar.getInstance();

    static {
        map.put("一", 1);
        map.put("两", 2);
        map.put("二",2);
        map.put("三", 3);
        map.put("四", 4);
        map.put("五", 5);
        map.put("六", 6);
        map.put("七", 7);
        map.put("八", 8);
        map.put("九", 9);
        map.put("十", 10);
        map.put("十一", 11);
        map.put("十二", 12);

    }

    public String getResultByWeChat(String word, String account) {
        String cutWord = pythonInvoke.cutWord(word);
        JSONArray array = JSONArray.parseArray(cutWord);
        CoreInquiryParam param = buildParam(array, account);

        return processParam(param);

    }

    private CoreInquiryParam buildParam(JSONArray array,String account) {

        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        String startYear = null;
        String endYear = null;
        String startMonth = null;
        String endMonth = null;
        String business = null;
        int season=0;
        List<String> places = new ArrayList<>();
        List<Integer> years = new ArrayList<>();
        List<Integer> seasons = new ArrayList<>();
        List<Integer> months = new ArrayList<>();

        for (int i = 0; i < array.size(); i++) {

            JSONObject object = array.getJSONObject(i);
            //处理 第 n 年
            if (object.containsKey("n")) {
                if (startYear == null) {
                    startYear = object.getString("n");
                    startYear = startYear.length() >= 4 ? startYear.substring(0, 4)
                            : 20 + startYear.substring(0, 2);
                } else{
                    endYear = object.getString("n");
                    endYear = endYear.length() >= 4 ? endYear.substring(0, 4)
                            : 20 + endYear.substring(0, 2);
                }


            }
            //  处理第 n 月
            if (object.containsKey("y")) {
                if (startMonth == null) {
                    startMonth = object.getString("y");
                    startMonth = startMonth.substring(0, startMonth.indexOf("月"));
                    if (map.containsKey(startMonth)) {
                        startMonth = String.valueOf(map.get(startMonth));
                    }

                } else {
                    endMonth = object.getString("y");
                    endMonth = endMonth.substring(0, endMonth.indexOf("月"));
                    if (map.containsKey(endMonth)) {
                        endMonth = String.valueOf(map.get(endMonth));
                    }
                }
            }


            //处理第n季度
            if (object.containsKey("j")) {

                String temp = object.getString("j").substring(1, 2);
                if (map.containsKey(temp)) {
                    season = map.get(temp);
                    if (season > 4 || season < 0)
                        season = getYeartQuarterIndex(calendar) - 1;
                }else {
                    season= Integer.parseInt(temp);
                    if (season > 4 || season < 0)
                        season = getYeartQuarterIndex(calendar) - 1;
                }


            }

            //处理前 n 年数据
            if (object.containsKey("b")) {
                String temp = object.getString("b").substring(1, 2);
                int value = 0;
                if (map.containsKey(temp))
                    value = map.get(temp);
                else value = Integer.parseInt(temp);
                for (int k = value; k > 0; k--)
                    years.add(currentYear - k);

            }


            //处理前 n 月
            if (object.containsKey("m")) {

                String m = object.getString("m").substring(1, 2);
                int value = 0;
                if (map.containsKey(m)) {
                    value = map.get(m);
                }
                else{
                    value = Integer.parseInt(m);
                }

                for (int k = value; k > 0; k--){
                    int temp = currentMonth - k;
                    if (temp>0)
                        months.add(temp);
                }

            }

            //处理前n季度
            if (object.containsKey("p")) {
                String temp = object.getString("p").substring(1, 2);
                int value = 0;
                if (map.containsKey(temp))
                    value = map.get(temp);
                else
                    value = Integer.parseInt(temp);
                int quarterIndex = getYeartQuarterIndex(calendar);
                if (value > 4 || value < 0|| value>=quarterIndex) {
                    for (int k = 1; k < quarterIndex; k++)
                        seasons.add(k);
                }else if (value<quarterIndex){
                    for (int k = 1; k < value; k++)
                        seasons.add(k);
                }
            }

            //  处理地点
            if (object.containsKey("c")) {
                String temp = (String) object.get("c");
                if (temp.contains("市") || temp.length() == 2) {
                    temp = temp.substring(0, 2) + "分行";
                }
                places.add(temp);
            }

            //处理业务
            if (object.containsKey("d")) {
                business = (String) object.get("d");
            }
        }

        CoreInquiryParam param = new CoreInquiryParam();

        if (business==null){
            business="开卡数";
        }
        param.setBusiness(business);
        if(places.isEmpty()){
            places.add(managerService.getUserBank(account));
        }
        if (startYear==null)
            startYear= String.valueOf(currentYear);
        if (endYear==null)
            endYear= startYear;
        if (startMonth==null)
            startMonth="1";
        if (endMonth==null)
            endMonth=startMonth;
        param.setPlaces(places);
        param.setStartYear(startYear);
        param.setEndYear(endYear);
        param.setStartMonth(Integer.parseInt(startMonth));
        param.setEndMonth(Integer.parseInt(endMonth));
        param.setSeason(season);
        param.setYears(years);
        param.setMonths(months);
        param.setSeasons(seasons);
        param.setCurrentMonth(currentMonth);
        param.setCurrentYear(currentYear);
        return param;

    }

    private String processParam(CoreInquiryParam param) {
        List<Integer> years = param.getYears();
        List<Integer> seasons = param.getSeasons();
        List<Integer> months = param.getMonths();
        int season = param.getSeason();
        List<String> places = param.getPlaces();
        String business = param.getBusiness();
        String startTime;
        String endTime;
        List<CardData> cardData=null;
        if (!years.isEmpty()){
             cardData = coreInquiry.inquiryByYears(param);
             startTime=years.get(0)+"-"+"01";
             endTime=years.get(years.size()-1)+"12";
        }else if (!seasons.isEmpty()){
             startTime=param.getCurrentYear()+"-"+seasons.get(0)*3;
             endTime=param.getCurrentYear()+"-"+seasons.get(seasons.size()-1)*3;
             cardData = coreInquiry.inquiryBySeasons(param);

        }else if (!months.isEmpty()){
            startTime=param.getCurrentYear()+"-"+months.get(0);
            endTime=param.getCurrentYear()+"-"+months.get(months.size()-1);
            cardData = coreInquiry.inquiryByMonths(param);

        }else if (season!=0){
            startTime=param.getCurrentYear()+"-"+(3*season-2);
            endTime=param.getCurrentYear()+"-"+season*3;
             coreInquiry.inquiryBySeason(param);
        }else {
            String startYear = param.getStartYear();
            String endYear = param.getEndYear();
            Integer startMonth = param.getStartMonth();
            Integer endMonth = param.getEndMonth();
             cardData = coreInquiry.inquiryByPeriod(param);
            startTime=startYear+"-"+startMonth;
            endTime=endYear+"-"+endMonth;

        }
        return JSONParseUtil.getSingleBusJson(startTime
                ,endTime,places,business,cardData);
    }

    /**
     * 当前属于本年第几个季度
     *
     * @return
     */
    private int getYeartQuarterIndex(Calendar calendar) {
        int month = calendar.get(Calendar.MONTH) + 1;
        if (month <= 3)
            return 1;
        else if (month <= 6)
            return 2;
        else if (month <= 9)
            return 3;
        else
            return 4;
    }


}
