package com.edu.orderdish;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class OrderdishApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderdishApplication.class, args);
    }
    @Bean
    public RestTemplate createRestTemplate(){
        return new RestTemplate();
    }

}
