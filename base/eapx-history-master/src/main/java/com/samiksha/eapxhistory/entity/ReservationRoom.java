package com.samiksha.eapxhistory.entity;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class ReservationRoom {
    private ObjectId roomId;

    private Double price;
}

