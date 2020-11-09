package com.max.payment.controller;

import com.google.gson.Gson;
import com.max.payment.DTO.Reservation;
import com.max.payment.VO.NormalVO;
import com.max.payment.VO.ResultVO;
import com.max.payment.domain.Email;
import com.max.payment.domain.Payment;
import com.max.payment.service.PaymentService;
import com.max.payment.service.ProductService;
import com.max.payment.tools.MessageTool;
import com.max.payment.tools.RestTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value("${api.key}")
    private String apiKey;
    @Value("${paypalUrl}")
    private String paypalUrl;

    @Value("${bankUrl}")
    private String bankUrl;

    @Value("${updateReservationUrl}")
    private String reservationUrl;

    @Value("${creditUrl}")
    private String creditUrl;
    private Gson gson = new Gson();


    @Autowired
    MessageTool messageTool;


    @PostMapping("/")
    public ResultVO<?> payment(@RequestBody Payment pay) {

//        header.ge
        String paymentUrl = "";
        String type = pay.getPaymentType();
        String reservationId = pay.getReservationId();


        if (type.equals("paypal")) {
            paymentUrl = paypalUrl;
        }


        //        else if (type.equals("bank")) {
//            paymentUrl = bankUrl;
//        }

        ResultVO<String> str = RestTool.request(paymentUrl + "/pay", "");

        if (str.getCode() == 0) {
            if (pay.getOriginalType().equals("DISH")) {
                Payment payment = paymentService.savePayment(pay);
                messageTool.sendMessage(gson.toJson(new Email("maxlimaoxuan@gmail.com", gson.toJson(payment))));
                return NormalVO.normalReturn("finish payment");
            } else if (pay.getOriginalType().equals("reservation")) {
                ResultVO<String> str1 = RestTool.request(reservationUrl, gson.
                        toJson(new Reservation(reservationId, "finish")));

                if (str1.getCode() == 0) {
                    Payment payment = paymentService.savePayment(pay);
                    messageTool.sendMessage(gson.toJson(new Email("maxlimaoxuan@gmail.com", gson.toJson(payment))));
                    return NormalVO.normalReturn("finish payment");
                }

            }
            return NormalVO.failReturn("wrong payment");

        } else {
            return NormalVO.failReturn("fail");
        }

    }

    @RequestMapping("/test")
    private boolean test() {
        return messageTool.sendMessage(gson.toJson(new Email("maxlimaoxuan@gmail.com", "sdsds")));
    }


}




