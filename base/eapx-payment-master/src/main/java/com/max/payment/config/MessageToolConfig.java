package com.max.payment.config;

import com.max.payment.tools.MessageTool;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MessageToolConfig {

    @Bean
    public MessageTool messageTool() {
        return new MessageTool();
    }

}
