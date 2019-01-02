package com.zy.demo;

import java.util.Random;

public class Test {

    private static final int BOUND = 100;

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();

        while (true){
            int first = random.nextInt(BOUND/2 - 1) +1;
            if (first == 0){
                break;
            }
            System.out.println(first);
            Thread.sleep(1000L);
        }
    }
}
