package com.max.eapxreservation.tools;

import com.max.eapxreservation.VO.RoomVO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestUtils {

    public static ResponseEntity<String> request(String url, String data) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<>(data, headers);
        RestTemplate template = new RestTemplate();
        return template.postForEntity(url, formEntity, String.class);
    }

    public static RoomVO requestRoom(String url, String data) {

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<>(data, headers);
        RestTemplate template = new RestTemplate();
        return template.postForEntity(url, formEntity, RoomVO.class).getBody();
    }



}
