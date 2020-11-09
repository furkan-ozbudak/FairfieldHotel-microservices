package com.edu.orderdish.controller;

import com.edu.orderdish.domain.Order;
import com.edu.orderdish.dto.Payment;
import com.edu.orderdish.service.impl.OrderService;
import com.edu.orderdish.vo.ResultVO;
import com.google.gson.Gson;
import org.bson.types.ObjectId;
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
public class OrderController {
    @Value("${api.key}")
    private String apiKey;
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    OrderService orderService;

    @Value("${router.dish.url}")
    private String dishUrl;

    @Value("${router.payment.url}")
    private String paymentUrl;

    private Gson gson = new Gson();

    @PostMapping("/addDishToOrder/{dishid}")//
//    public ResultVO<Order> addDishToOrder(@PathVariable long dishid,@RequestHeader("email") String email){
    public ResultVO<Order> addDishToOrder(@RequestParam String email,@PathVariable long dishid){
        ResultVO<Order> resultVO =  new ResultVO<Order>();

        Order order =orderService.addDishToOrder(email,dishid);
        //add to order
        resultVO.setCode(0);
        resultVO.setData(order);
        return resultVO;
    }

    @GetMapping("/getOrderList")//@RequestHeader("email") String email
    public ResultVO<List<Order>> getOrderList(@RequestParam String email){
//    public ResultVO<List<Order>> getOrderList(){
//        String email="email";
        ResultVO<List<Order>> resultVO =  new ResultVO<List<Order>>();
        resultVO.setCode(0);
        List<Order> orderList = orderService.getOrderListByEmail(email);
        resultVO.setData(orderList);

        return resultVO;
    }

//    @PostMapping("/cancelOrder/{orderid}")//
//    public ResultVO<Order> cancelOrder(@PathVariable ObjectId objectId ){
//        ResultVO<Order> resultVO =  new ResultVO<Order>();
//        Order order  = orderService.getOrderById(objectId);
//        if(order.getStatus().equals("DRAFT") || order.getStatus().equals("DELIVERED")) {
//            resultVO.setCode(1);
//            resultVO.setMsg("error!this order can not be canceled:");
//            return resultVO;
//        }
//        order.setStatus("CANCELED");
//        orderService.updateOrder(order);
//        resultVO.setCode(0);
//        resultVO.setMsg("success!order canceled:");
//        return resultVO;
//    }
//
//    @PostMapping("/deliverOrder/{orderid}")//
//    public ResultVO<Order> deliverOrder(@PathVariable ObjectId objectId){
//        ResultVO<Order> resultVO =  new ResultVO<Order>();
//        Order order  = orderService.getOrderById(objectId);
//        if(!order.getStatus().equals("SUCCESS") ) {
//            resultVO.setCode(1);
//            resultVO.setMsg("error!this order is not success!");
//            return resultVO;
//        }
//        order.setStatus("DELIVERED");
//        orderService.updateOrder(order);
//        resultVO.setCode(0);
//        resultVO.setMsg("success!order canceled:");
//        return resultVO;
//    }

    @PostMapping("/placeOrder")//@RequestHeader("email") String email
    public ResultVO<Order> placeOrder(@RequestParam String email){
//    public ResultVO<Order> placeOrder(){
//        String email="email";
        ResultVO<Order> resultVO =  new ResultVO<Order>();

        Order order  = orderService.getDraftOrderByEmail(email);
        if(order==null){
            resultVO.setCode(1);
            resultVO.setMsg("there is no dish ordered");
        }else{
            order.setStatus("SUCCESS");
            //call product to calculate the total price
            ResponseEntity<String> responseEntity = request(dishUrl+"/getTotalPrice",gson.toJson(order));
            System.out.println(responseEntity.getBody());
            ResultVO<Object> resultVO1= (ResultVO<Object>) this.JSONToObject(responseEntity.getBody(),ResultVO.class);
            System.out.println(resultVO1.getData());
            double totalPrice = Double.parseDouble(resultVO1.getData().toString());

            //call the payment to pay the money
            Payment payment = new Payment();
            payment.setReservationId(order.getId().toString());
            payment.setOriginalType("DISH");
            payment.setPrice(totalPrice);
            payment.setPaymentType("paypal");
            try{
                ResponseEntity<String> responseEntity2 = request(paymentUrl+"/payment/",gson.toJson(payment));
                System.out.println(responseEntity2.getBody());
                ResultVO<Object> resultVO2= (ResultVO<Object>) this.JSONToObject(responseEntity2.getBody(),ResultVO.class);
                System.out.println(resultVO2.getData());
            }catch (Exception exception){
                resultVO.setCode(1);
                resultVO.setMsg("call payment error!"+paymentUrl+"/payment:"+gson.toJson(payment));
                return resultVO;
            }finally {

            }

            orderService.updateOrder(order);
            resultVO.setCode(0);
            resultVO.setMsg("success!totalcost:"+totalPrice);
        }
        resultVO.setData(order);
        return resultVO;
    }

    public static String beanToJSONString(Object bean) {
        return new Gson().toJson(bean);
    }

    public static Object JSONToObject(String json,Class beanClass) {
        Gson gson = new Gson();
        Object res = gson.fromJson(json, beanClass);
        return res;
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
