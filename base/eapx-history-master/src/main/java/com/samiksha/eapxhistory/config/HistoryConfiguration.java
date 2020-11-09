package com.samiksha.eapxhistory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HistoryConfiguration {
    @Bean
    public Config config() {
        return new Config();
    }
}
