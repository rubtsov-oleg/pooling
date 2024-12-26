package com.factor.pooling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.factor.pooling")
public class PoolingApplication {

    public static void main(String[] args) {
        SpringApplication.run(PoolingApplication.class, args);
    }

}
