package com.edu.foodordering.dto;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class Order {

    ObjectId id;
    String email;
    List<OrderDetail> orderDetailList;
    String status;
}
