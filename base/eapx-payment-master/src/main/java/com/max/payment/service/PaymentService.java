package com.max.payment.service;

import com.max.payment.dao.PaymentDao;
import com.max.payment.domain.Payment;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentDao paymentDao;


    public Payment savePayment(Payment payment) {
        return paymentDao.save(payment);
    }



}
