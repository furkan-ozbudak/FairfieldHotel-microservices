package com.edu.orderdish.repository;

import com.edu.orderdish.domain.Order;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, ObjectId> {
    //@Query(value = "select * from orders where email=?1 and status='DRAFT'",nativeQuery = true)
    List<Order> findByEmailAndStatus(String email,String status);

    //@Query(value = "select * from orders where email=?1 ",nativeQuery = true)
    List<Order>  findByEmail(String email);
}
