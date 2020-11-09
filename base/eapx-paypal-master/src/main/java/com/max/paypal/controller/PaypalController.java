package com.max.paypal.controller;

import com.max.paypal.VO.ResultVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paypal")
public class PaypalController {


    @RequestMapping("/pay")
    public ResultVO<String> pay() {
        ResultVO<String> res = new ResultVO<>();
        res.setCode(0);
        res.setMsg("success");
        res.setData("{}");
        return res;
    }

}
