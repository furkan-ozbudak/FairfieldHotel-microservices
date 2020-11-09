package com.edu.orderdish.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Setter
@Getter
@Document
public class OrderDetail {

    @Id
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
