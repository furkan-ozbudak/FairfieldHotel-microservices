package edu.mum.ea_auth;

import edu.mum.common.JWTUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EaAuthApplication {

    @Value("${jwt_key}")
    private String jwt_key;

    public static void main(String[] args) {
        SpringApplication.run(EaAuthApplication.class, args);
    }

    @Bean
    public JWTUtil JWTUtil(){
        JWTUtil jwtUtil = new JWTUtil();
        jwtUtil.setKey(jwt_key);
        return jwtUtil;
    }
}
