package com.techelevator.tenmo.services;


import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountService {

    public static final String API_BASE_URL = "http://localhost:8080/account";
    private RestTemplate restTemplate = new RestTemplate();
    private String authToken = null;
    public void setAuthToken (String authToken) {
        this.authToken = authToken;
    }

    public BigDecimal getUserBalance(){
        ResponseEntity<BigDecimal> balance = restTemplate.exchange(API_BASE_URL, HttpMethod.GET,
                makeBalanceEntity(), BigDecimal.class);
        return balance.getBody();

    }

    private HttpEntity<BigDecimal> makeBalanceEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
