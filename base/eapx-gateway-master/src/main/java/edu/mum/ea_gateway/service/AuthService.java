package edu.mum.ea_gateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    @Value("${authUrl}")
    private String authUrl;
    @Autowired
    private RestTemplate restTemplate;
    public boolean verify(String token){
        ResponseEntity<Boolean> responseEntity = restTemplate.getForEntity(authUrl+"/auth/verify/"+token,Boolean.class);
        if (responseEntity.getStatusCode()== HttpStatus.OK){
            return true;
        }
        return false;
    }
}
