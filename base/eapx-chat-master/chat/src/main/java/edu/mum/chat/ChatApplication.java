package edu.mum.chat;

import edu.mum.chat.tools.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
@EnableCaching
@SpringBootApplication
public class ChatApplication {

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(0,0);
    }

    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }

}
