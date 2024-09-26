package com.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;

@Configuration
public class MongoConfig {
  @Bean
  public MongoTransactionManager mongoDBTransactionManager(MongoDatabaseFactory databaseFactory) {
    return new MongoTransactionManager(databaseFactory);
  }
}
