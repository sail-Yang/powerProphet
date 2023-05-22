package com.sailyang.powerprophet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.sailyang.powerprophet.dao")
public class PowerProphetApplication {

    public static void main(String[] args) {
        SpringApplication.run(PowerProphetApplication.class, args);
    }

}
