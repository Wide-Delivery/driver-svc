package com.widedelivery.driver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class DriverSvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(DriverSvcApplication.class, args);
    }

}
