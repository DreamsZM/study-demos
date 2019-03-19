package com.zy.date;

import java.time.Instant;

public class Test {
    public static void main(String[] args) {
        String dateStr = DateUtils.format(Instant.now());
        System.out.println(dateStr);
    }
}
