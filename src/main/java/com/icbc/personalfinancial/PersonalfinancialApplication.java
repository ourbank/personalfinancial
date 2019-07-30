package com.icbc.personalfinancial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication

public class PersonalfinancialApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalfinancialApplication.class, args);
    }

}
