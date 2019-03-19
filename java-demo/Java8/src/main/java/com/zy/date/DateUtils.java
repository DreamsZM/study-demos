package com.zy.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss");

    public static String format(LocalDateTime localDateTime){
        return dateTimeFormatter.format(localDateTime);
    }

    public static String format(Instant instant){
        return dateTimeFormatter.format(instant);
    }

    public static LocalDateTime parseByDateTimeFormatter(String dateStr){
        return LocalDateTime.parse(dateStr, dateTimeFormatter);
    }

    private static ThreadLocal<SimpleDateFormat> simpleDateFormatThreadLocal = ThreadLocal.withInitial(
            () -> new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
    );

    private static ThreadLocal<String> stringThreadLocal = new ThreadLocal<String>(){
        @Override
        protected String initialValue() {
            return super.initialValue();
        }
    };

    public static String format(Date date){
        return simpleDateFormatThreadLocal.get().format(date);
    }

    public static Date parse(String dateStr) throws ParseException {
        return simpleDateFormatThreadLocal.get().parse(dateStr);
    }
}
