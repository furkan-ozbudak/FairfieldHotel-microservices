package com.max.eapxreservation.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Document(collection = "reservation")
public class Reservation {

    @Id
    private ObjectId id;

    private String clientId;

//    private String date;

    private String roomId;

    private String firstDay;

    private String lastDay;


//    private String paymentMethod;

    private String status;

    @CreatedDate
    private Long createdAt;

    @LastModifiedDate
    private Long updatedAt;

}
