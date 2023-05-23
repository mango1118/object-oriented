package com.oo;

/**
 * @description:
 * @author: mango
 * @date: ${DATE} ${TIME}
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class LearningSystemApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(LearningSystemApplication.class);
    }

    public static void main(String[] args) {

        SpringApplication.run(LearningSystemApplication.class, args);
    }

}
