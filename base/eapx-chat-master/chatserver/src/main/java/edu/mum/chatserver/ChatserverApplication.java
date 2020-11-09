package edu.mum.chatserver;

import edu.mum.chatserver.server.ChatServer;
import edu.mum.chatserver.server.MQController;
import edu.mum.chatserver.server.MsgController;
import edu.mum.chatserver.service.StatusService;
import edu.mum.chatserver.tools.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class ChatserverApplication implements CommandLineRunner {
    @Autowired
    private ChatServer chatServer;

    @Autowired
    private StatusService statusService;


    @Bean
    public IdWorker idWorker(){
        return new IdWorker(0,0);
    }

    public static void main(String[] args) {
        SpringApplication.run(ChatserverApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        statusService.clear();
        chatServer.start();

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                System.out.println("clear");
                statusService.clear();
            }
        });
    }

}
