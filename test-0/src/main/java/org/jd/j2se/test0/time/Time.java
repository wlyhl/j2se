package org.jd.j2se.test0.time;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by cuijiandong on 2018/8/2.
 */
public class Time {
    public static void main(String[] a) throws ParseException {
//        Date now =new Date();
//        Date d1= new SimpleDateFormat("yyyy-MM-dd").parse("2018-08-03");
//        Date d2= new SimpleDateFormat("yyyy-MM-dd").parse("2018-08-04");
//        long c=(d2.getTime()-d1.getTime())/(24*60*60*1000);
        Date date = DateUtils.parseDate("2018-09-01", "yyyy-MM-dd");

        Calendar now = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();
        System.out.println(now.before(endTime));
    }
}
