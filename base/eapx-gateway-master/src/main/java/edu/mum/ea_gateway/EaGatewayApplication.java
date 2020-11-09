package edu.mum.ea_gateway;

import edu.mum.ea_gateway.filter.PreFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableZuulProxy
@SpringBootApplication
public class EaGatewayApplication {

    @Bean
    public PreFilter preFilter() {
        return new PreFilter();
    }

    public static void main(String[] args) {
        SpringApplication.run(EaGatewayApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
