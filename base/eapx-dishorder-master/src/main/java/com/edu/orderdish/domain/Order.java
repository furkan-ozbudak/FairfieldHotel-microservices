package com.edu.orderdish.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Setter
@Getter
@Document
//@Table(name="orders")
public class Order {

    @Id
    @JsonIgnore
    ObjectId id;
    String email;
    List<OrderDetail> orderDetailList;
    String status;
}
