package com.edu.foodordering.controller;

import com.edu.foodordering.domain.Dish;
import com.edu.foodordering.dto.Order;
import com.edu.foodordering.dto.OrderDetail;
import com.edu.foodordering.service.impl.DishService;
import com.edu.foodordering.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class DishController {
    @Value("${api.key}")
    private String apiKey;
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    DishService dishService;

    @PostMapping("/addDish")
    public ResultVO<Dish> addDish(@RequestBody Dish dish){
        ResultVO<Dish> resultVO =  new ResultVO<Dish>();
        resultVO.setCode(0);
        Dish  dishresult= dishService.addDish(dish);
        resultVO.setData(dishresult);

        return resultVO;
    }
    @DeleteMapping("/removeDish/{dishId}")
    public ResultVO<Dish> removeDish(@PathVariable long dishId){
        ResultVO<Dish> resultVO =  new ResultVO<Dish>();
        resultVO.setCode(0);

        dishService.removeDish(dishId);
        return resultVO;
    }
    @PostMapping("/getDishList")
    public ResultVO<List<Dish>> getDishList(@RequestBody Dish dish){
        ResultVO<List<Dish>> resultVO =  new ResultVO<List<Dish>>();

        List<Dish> dishList  = dishService.getDishList();
        resultVO.setCode(0);
        resultVO.setData(dishList);

        return resultVO;
    }
    @PostMapping("/updateDish")
    public ResultVO<Dish> updateDish(@RequestBody Dish dish){
        ResultVO<Dish> resultVO =  new ResultVO<Dish>();
        resultVO.setCode(0);
        Dish dishresult = dishService.updateDish(dish);
        resultVO.setData(dishresult);

        return resultVO;
    }

    @PostMapping("/getTotalPrice")
    public ResultVO<Double> getTotalPrice(@RequestBody Order order){
        ResultVO<Double> resultVO =  new ResultVO<Double>();
        resultVO.setCode(0);

        resultVO.setData(dishService.getTotalPrice(order));

        return resultVO;
    }

    private ResponseEntity<String> request(String authUrl, String data) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("api-key", apiKey);
        HttpEntity<String> formEntity = new HttpEntity<>(data, headers);
        return restTemplate.postForEntity(authUrl, formEntity, String.class);
    }
}
