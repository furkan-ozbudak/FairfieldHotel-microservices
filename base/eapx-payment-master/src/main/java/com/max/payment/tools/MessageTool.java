package com.max.payment.tools;

import com.google.gson.Gson;
import com.max.payment.domain.Email;
import com.max.payment.service.PaymentService;
import com.max.payment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

public class MessageTool {




    @Autowired
    private ProductService producerService;

    public boolean sendMessage(String str) {

//        String str = gson.toJson(new Email("maxlimaoxuan@gmail.com", "sds"));
        System.out.println(str);
        Message<String> message = MessageBuilder.withPayload(str).build();
//        boolean result =
        return producerService.sendMessage().send(message);
    }
}
