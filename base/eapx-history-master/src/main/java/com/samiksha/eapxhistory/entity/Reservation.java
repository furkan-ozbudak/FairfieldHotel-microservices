package com.samiksha.eapxhistory.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "reservation")
public class Reservation {
    @Id
    private ObjectId id;

    private ObjectId userId;

    private List<ReservationRoom> reservationRoomList;

    private String paymentMethod;

    private String status;

    private Date bookingDate;

    private Date checkinDate;

    private Date checkoutDate;

    private Double dailyRate;

    private Double tax;

    private Double serviceFee;

    private Double totalCost;

}
