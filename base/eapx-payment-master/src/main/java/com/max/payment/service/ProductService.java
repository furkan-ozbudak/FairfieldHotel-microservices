package com.max.payment.service;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;

public interface ProductService {

    String email = "mail";

    @Output(ProductService.email)
    SubscribableChannel sendMessage();
}
