package com.codervibe.rizhi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Administrator
 */
@SpringBootApplication
@MapperScan("com.codervibe.rizhi.dao")
public class RizhiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RizhiApplication.class, args);
    }

}
