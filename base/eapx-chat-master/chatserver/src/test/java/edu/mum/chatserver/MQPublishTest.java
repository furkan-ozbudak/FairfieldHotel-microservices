package edu.mum.chatserver;

import edu.mum.chatserver.server.MQController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class MQPublishTest {


    public static void main(String[] args) throws IOException, TimeoutException {
        MQController mqController = new MQController();
        mqController.publish("hello.hi","hello");
    }
}