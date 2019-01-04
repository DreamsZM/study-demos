package com.zy.demo.netty.test;

import com.zy.demo.common.utils.DateUtil;
import com.zy.demo.common.utils.ThreadPoolUtil;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        ScheduledExecutorService scheduledExecutorService = ThreadPoolUtil.getScheduledThreadPool(3);
        ScheduledFuture scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(()->{
            System.out.println("Hello " + atomicInteger.getAndIncrement() + " " + Thread.currentThread().getName());

        }, 0, 2, TimeUnit.SECONDS);
//        scheduledFuture.get();
        System.out.println("test");
        scheduledExecutorService.scheduleAtFixedRate(()->{
            System.out.println("world " + atomicInteger.getAndIncrement() + " " + Thread.currentThread().getName());
        }, 1, 2, TimeUnit.SECONDS);

    }
}
