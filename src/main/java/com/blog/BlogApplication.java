package com.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
/*
 * This is the main class of the blog application.
 * It is annotated with @SpringBootApplication to mark it as the entry point of the application.
 * It is also annotated with @EnableTransactionManagement to enable transaction management for the application.
 */
public class BlogApplication {

  public static void main(String[] args) {
    SpringApplication.run(BlogApplication.class);
  }
}
