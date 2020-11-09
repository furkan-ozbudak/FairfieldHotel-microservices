package com.max.payment.domain;

import lombok.Data;

@Data
public class Email {
    private String email;
    private String content;

    public Email(String email, String content) {
        this.email = email;
        this.content = content;
    }
}
