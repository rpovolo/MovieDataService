package com.evaluation.movies;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MovieDataApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(MovieDataApplication.class)
                .build()
                .run(args);
    }
}
