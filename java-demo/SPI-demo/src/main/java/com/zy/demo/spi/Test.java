package com.zy.demo.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

public class Test {

    public static void main(String[] args) {
        ServiceLoader<SPI> serviceLoader = ServiceLoader.load(SPI.class);
        Iterator<SPI> iterator = serviceLoader.iterator();
        while (iterator.hasNext()){
            SPI spi = iterator.next();
            spi.execute();
            System.out.println("=================");
        }
    }
}
