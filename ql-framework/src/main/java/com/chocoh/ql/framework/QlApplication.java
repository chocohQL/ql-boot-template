package com.chocoh.ql.framework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chocoh
 */
@SpringBootApplication(scanBasePackages =  "com.chocoh.ql")
public class QlApplication {
    public static void main(String[] args) {
        SpringApplication.run(QlApplication.class, args);
    }
}
