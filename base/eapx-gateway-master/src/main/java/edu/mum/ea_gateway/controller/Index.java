package edu.mum.ea_gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Index {
    @RequestMapping("/")
    public String say(){
        return "Gateway id OK!";
    }
}
