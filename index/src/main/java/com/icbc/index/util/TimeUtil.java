package com.icbc.index.util;





import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    public static String getYearMonth(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月");
        String dateString = formatter.format(date);
        //System.out.println(dateString);
        return dateString;
    }

    public static String getYear(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年");
        String dateString = formatter.format(date);
       // System.out.println(dateString);
        return dateString;
    }

    /**
     * 将不同格式的日期格式字符串 转化 为date格式，方便进行数据库存储
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = null;
        if(strDate.contains("号"))
            formatter = new SimpleDateFormat("yyyy年MM月dd号");
        else if(strDate.contains("月"))
            formatter = new SimpleDateFormat("yyyy年MM月");
        else
            formatter = new SimpleDateFormat("yyyy年");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    public static String dateToFormatStr(Date date,String format){
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(date);
        // System.out.println(dateString);
        return dateString;
    }

    public static int dateToFormatInt(Date date,String format){
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(date);
        int dateInt = Integer.parseInt(dateString);
        return dateInt;
    }

    public static String dateToYearStr(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        String dateString = formatter.format(date);
        // System.out.println(dateString);
        return dateString;
    }

    public static String dateToMonthStr(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        String dateString = formatter.format(date);
        // System.out.println(dateString);
        return dateString;
    }

    public static String dateToDayStr(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        // System.out.println(dateString);
        return dateString;
    }

    // 获取下个月第一天
    public static String getNextMonnth(Date date){
        String year = new SimpleDateFormat("yyyy").format(date);
        if((Integer.parseInt(new SimpleDateFormat("MM").format(date))+ 1) >=13){
            return getNextYear(date);
        }
        String month = String.valueOf(Integer.parseInt(new SimpleDateFormat("MM").format(date))+ 1);
        return year+"-"+month+"-01";
    }

    public static String getNextYear(Date date) {
        String year = String.valueOf(Integer.parseInt(new SimpleDateFormat("yyyy").format(date))+ 1);
        return year+"01-01";
    }

    public static int getDayOfMonth(int year,int month){
        Calendar c = Calendar.getInstance();
        c.set(year, month, 0); //输入类型为int类型
        return c.get(Calendar.DAY_OF_MONTH);
    }


    public static int getTimePeriod(String start, String end){
        //"2018-2","2019-10"
        int startYear = Integer.parseInt(start.substring(0,4));
        int endYear = Integer.parseInt(end.substring(0,4));
        int startMon = Integer.parseInt(start.substring(5));
        int endMon = Integer.parseInt(end.substring(5));
        return (endYear-startYear) * 12 + endMon - startMon;
    }

    public static String  getNextMontStr(String now){
        int nowYear = Integer.parseInt(now.substring(0,4));
        int nowMon = Integer.parseInt(now.substring(5));
        if(nowMon != 12){
            return ""+nowYear+"-"+(nowMon + 1);
        }
        return ""+(nowYear+1)+"-01";
    }

    public static String praseStartTime(String time){
        time = time.substring(0,4);
        return time + "-01-01 00:00:00";
    }

    public static String praseEndTime(String time){
        time = time.substring(0,4);
//        int nowYear = Integer.parseInt(time.substring(0,4));
//        int nowMon = Integer.parseInt(time.substring(5));
//        return time+"-"+getDayOfMonth(nowYear,nowMon)+" 23:59:59";
        return time + "-12-31 23:59:59";
    }

}
