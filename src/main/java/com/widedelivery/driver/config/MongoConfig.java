package com.widedelivery.driver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
//        (dateTimeProviderRef = "auditingDateTimeProvider")
public class MongoConfig {

//    public @Bean com.mongodb.client.MongoClient mongoClient() {
//        return com.mongodb.client.MongoClients.create("mongodb://root:example@localhost:27017/order-service?authSource=admin");
//    }
//
//    @Bean(name = "auditingDateTimeProvider")
//    public DateTimeProvider dateTimeProvider() {
//        return () -> Optional.of(OffsetDateTime.now());
//    }

}
