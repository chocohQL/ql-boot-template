package com.chocoh.ql.framework;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chocoh
 */
@MapperScan(basePackages = {"com.chocoh.ql.dal.mapper"})
@SpringBootApplication(scanBasePackages =  "com.chocoh.ql")
public class QlApplication {
    public static void main(String[] args) {
        SpringApplication.run(QlApplication.class, args);
    }
}
