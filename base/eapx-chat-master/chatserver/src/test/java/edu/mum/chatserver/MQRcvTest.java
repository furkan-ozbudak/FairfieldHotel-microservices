package edu.mum.chatserver;

import edu.mum.chatserver.server.MQController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootTest
@SpringBootApplication
public class MQRcvTest {

    public static void main(String[] args) throws IOException, TimeoutException {
        MQController mqController = new MQController();
        mqController.startRcv();
        mqController.bindKey("hello.hi");
    }

}