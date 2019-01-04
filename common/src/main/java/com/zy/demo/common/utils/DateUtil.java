package com.zy.demo.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class DateUtil {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat();

    public static String getFormatDate(String pattern){
        if (pattern == null || pattern.equals("")){
            log.warn("pattern is null");
        }
        dateFormat.applyPattern(pattern);
        return dateFormat.format(new Date());
    }

    public static String getFormatDate(){
        return getFormatDate("yyyy/MM/dd hh:MM:ss");
    }

    public static void main(String[] args) {
        String pattern = "";
        System.out.println(getFormatDate(pattern));
    }
}
