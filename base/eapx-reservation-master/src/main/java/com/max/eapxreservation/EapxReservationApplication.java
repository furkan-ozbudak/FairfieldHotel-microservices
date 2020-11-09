package com.max.eapxreservation;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class EapxReservationApplication {

    public static void main(String[] args) {
        SpringApplication.run(EapxReservationApplication.class, args);
    }

}
