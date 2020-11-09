package com.edu.foodordering.repository;


import com.edu.foodordering.domain.Dish;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepository extends MongoRepository<Dish, Long> {

}
