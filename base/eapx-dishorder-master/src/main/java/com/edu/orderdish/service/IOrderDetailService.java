package com.edu.orderdish.service;

import com.edu.orderdish.domain.OrderDetail;
import org.bson.types.ObjectId;

public interface IOrderDetailService {
    public int dishSetCount(ObjectId orderDetailId, int count);
    public OrderDetail addOrderDetail(OrderDetail orderDetail);

}
