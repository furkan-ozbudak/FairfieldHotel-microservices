package com.edu.foodordering.service;

import com.edu.foodordering.domain.Dish;
import com.edu.foodordering.dto.Order;

import java.util.List;

public interface IDishService {
    public Dish addDish(Dish dish);
    public void removeDish(long dishId);
    public Dish updateDish(Dish dish);
    public List<Dish> getDishList();
    public double getTotalPrice(Order order);
}
