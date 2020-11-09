package com.edu.foodordering.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;


@Data
public class OrderDetail {

    @Id
    @JsonIgnore
    ObjectId id;
    long dishid;
    int count;

    public OrderDetail(long dishid, int count) {
        this.dishid = dishid;
        this.count = count;
    }

    public OrderDetail() {
    }
}
