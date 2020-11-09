package com.max.payment.DTO;

import lombok.Data;

@Data
public class Reservation {
    private String id;
    private String status;

    public Reservation(String id, String status) {
        this.id = id;
        this.status = status;
    }
}
