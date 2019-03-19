package com.zy.date;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 20; i++) {
            executorService.execute(() -> {
                String dateStr = DateUtils.format(new Date());
                System.out.println(dateStr);
            });
        }
    }
}
