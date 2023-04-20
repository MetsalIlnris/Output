package com.output;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.output.dao")
public class OutputApplication {

    public static void main(String[] args) {
        SpringApplication.run(OutputApplication.class, args);
    }

}
