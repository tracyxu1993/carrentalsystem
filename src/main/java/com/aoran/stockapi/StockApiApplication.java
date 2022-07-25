package com.aoran.stockapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.aoran.stockapi.mapper")
public class StockApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockApiApplication.class, args);
    }

}
