package com.edu.orderdish.service.impl;

import com.edu.orderdish.domain.OrderDetail;
import com.edu.orderdish.repository.OrderDetailRepository;
import com.edu.orderdish.service.IOrderDetailService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailService implements IOrderDetailService {
    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Override
    public int dishSetCount(ObjectId orderDetailId, int count) {
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId).get();
        orderDetail.setCount(count);
//        orderDetailRepository.deleteById(orderDetail.getId());
//        orderDetailRepository.insert(orderDetail);
        orderDetailRepository.save(orderDetail);
        return orderDetail.getCount();
    }

    @Override
    public OrderDetail addOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }
}
