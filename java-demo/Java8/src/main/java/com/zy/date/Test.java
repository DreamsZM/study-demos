package com.zy.date;

import java.util.Date;

public class Test {
    public static void main(String[] args) {
        String dateStr = DateUtils.format(new Date());
        System.out.println(dateStr);
    }
}
