package com.edu.orderdish.dto;

import lombok.Data;

@Data
public class Payment {
    String reservationId;
    String originalType;
    double price;
    String paymentType;
}
