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
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class LearningSystemApplication extends SpringBootServletInitializer {


    public static void main(String[] args) {
        SpringApplication.run(LearningSystemApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(LearningSystemApplication.class);
    }


}
