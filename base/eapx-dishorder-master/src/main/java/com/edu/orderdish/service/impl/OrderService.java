package com.edu.orderdish.service.impl;

import com.edu.orderdish.domain.Order;
import com.edu.orderdish.domain.OrderDetail;
import com.edu.orderdish.repository.OrderRepository;
import com.edu.orderdish.service.IOrderService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService implements IOrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailService orderDetailService;

    public Order getOrderById(ObjectId id) {
        return orderRepository.findById(id).get();
    }
    public List<Order> getOrderListByEmail(String email){
        return orderRepository.findByEmail(email);
    }

    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order addDishToOrder(String email, long dishid) {
        Order order = this.getDraftOrderByEmail(email);
        if(order==null) {
            order = new Order();
            order.setEmail(email);
            order.setOrderDetailList(new ArrayList<OrderDetail>());
            OrderDetail orderDetail = new OrderDetail(dishid,1);
            order.setStatus("DRAFT");
            order.getOrderDetailList().add(orderDetail);
//            orderDetailService.addOrderDetail(orderDetail);
            order = orderRepository.save(order);
        }
        else{
            OrderDetail orderDetail1= null;
            for(OrderDetail orderDetail:order.getOrderDetailList()){
                if(orderDetail.getDishid()==dishid){
                    orderDetail1 = orderDetail;
                    break;
                }
            }
            if(orderDetail1!=null) {
//                orderDetailService.dishSetCount(orderDetail1.getId(),orderDetail1.getCount()+1);
                orderDetail1.setCount(orderDetail1.getCount()+1);
            }else{
                orderDetail1 = new OrderDetail(dishid,1);
                orderDetail1.setCount(1);
                orderDetail1.setDishid(dishid);
                order.getOrderDetailList().add(orderDetail1);
//                orderRepository.save(order);
            }
            orderRepository.save(order);
        }

        return this.getDraftOrderByEmail(email);
    }

    @Override
    public Order getDraftOrderByEmail(String email) {
        List<Order> orderList = orderRepository.findByEmailAndStatus(email,"DRAFT");
        if(orderList.size()>0)
            return orderList.get(0);
        return null;
    }

    @Override
    public Order createOrder() {
        return null;
    }

    @Override
    public Order addDishToOrder(ObjectId orderid, long dishid) {
        return null;
    }

    public void deleteOrder(ObjectId orderid) {

    }
}
