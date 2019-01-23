package com.zy.demo.spi;

public class SPIImpl2 implements SPI {
    @Override
    public void execute() {
        System.out.println(SPIImpl2.class.getSimpleName());
    }
}
