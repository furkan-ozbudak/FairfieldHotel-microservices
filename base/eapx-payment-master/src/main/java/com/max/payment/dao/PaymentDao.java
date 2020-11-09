package com.max.payment.dao;

import com.max.payment.domain.Payment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDao extends MongoRepository<Payment, ObjectId> {
}
