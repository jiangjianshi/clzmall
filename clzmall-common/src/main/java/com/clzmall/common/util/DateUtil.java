package com.clzmall.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jiangjianshi on 18/8/19.
 */
public class DateUtil {

    public  static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";


    public static String formatDateTime(Date date){
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern(yyyyMMddHHmmss);
        return format.format(date);
    }

}
