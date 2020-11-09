package com.edu.orderdish.service;

import com.edu.orderdish.domain.Order;
import org.bson.types.ObjectId;

import java.util.List;

public interface IOrderService {
    public Order getOrderById(ObjectId id);
    public Order createOrder();
    public Order addDishToOrder(ObjectId orderid, long dishid);
    public void deleteOrder(ObjectId orderid);
    public Order getDraftOrderByEmail(String email);
    public List<Order> getOrderListByEmail(String email);
}
