package com.sparta.masterslavetest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class MasterSlaveTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MasterSlaveTestApplication.class, args);
    }

}
