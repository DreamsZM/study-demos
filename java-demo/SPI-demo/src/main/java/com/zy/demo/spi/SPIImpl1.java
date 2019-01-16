package com.zy.demo.spi;

public class SPIImpl1 implements SPI {
    @Override
    public void execute() {
        System.out.println(SPIImpl1.class.getSimpleName());
    }
}
