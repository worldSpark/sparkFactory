package jmm01;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: java-vip-course
 * @description:
 * @author: Mr.Wan
 * @create: 2021-04-22 10:40
 **/
public class Demo {
    public static String getDatePoor(Date endDate, Date nowDate) {

        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    public static String getDateFormate(String endDate, String nowDate) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = df.parse(nowDate);
        Date date=df.parse(endDate);
        long l=now.getTime()-date.getTime();
        long day=l/(24*60*60*1000);
        long hour=(l/(60*60*1000)-day*24);
        long min=((l/(60*1000))-day*24*60-hour*60);
        long s=(l/1000-day*24*60*60-hour*60*60-min*60);
        System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");
        return ""+day+"天"+hour+"小时"+min+"分"+s+"秒";
    }


    public static void main(String[] args) throws ParseException {
        getDateFormate("2018-05-29 14:15:46","2021-04-21 23:25:03");
    }
}
