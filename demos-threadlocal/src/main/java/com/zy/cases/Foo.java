package com.zy.cases;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 避免???【并发】??? => 同步，包装非线程安全的对象，实现同步访问的效果
 */
public class Foo {

    private static final ThreadLocal<SimpleDateFormat> formatter = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat();
        }
    };

    public String formatDate(Date date){
        return formatter.get().format(date);
    }

    public static void main(String[] args) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String dateString = simpleDateFormat.format(new Date());
        System.out.println(dateString);
    }
}
