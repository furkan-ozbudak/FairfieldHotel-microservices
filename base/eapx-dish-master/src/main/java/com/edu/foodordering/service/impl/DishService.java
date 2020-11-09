package com.edu.foodordering.service.impl;

import com.edu.foodordering.domain.Dish;
import com.edu.foodordering.dto.Order;
import com.edu.foodordering.dto.OrderDetail;
import com.edu.foodordering.repository.DishRepository;
import com.edu.foodordering.service.IDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DishService implements IDishService {
    @Autowired
    DishRepository dishRepository;

    @Override
    public Dish addDish(Dish dish) {
        return dishRepository.save(dish);
    }

    @Override
    public void removeDish(long dishId) {
        dishRepository.deleteById(dishId);
    }

    @Override
    public Dish updateDish(Dish dish) {
        dishRepository.deleteById(dish.getId());
        return dishRepository.save(dish);
    }

    @Override
    public List<Dish> getDishList() {
        return (List<Dish>)dishRepository.findAll();
    }

    @Override
    public double getTotalPrice(Order order) {
        double totalPrice=0.0;
        for(OrderDetail orderDetail:order.getOrderDetailList()){
            totalPrice+=dishRepository.findById(orderDetail.getDishid()).get().getPrice()*orderDetail.getCount();
        }
        return totalPrice;
    }
}
