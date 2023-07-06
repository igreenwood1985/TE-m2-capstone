package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransferService {
    private static final String API_BASE_URL = "http://localhost:8080/transfer";

    private RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;

    public void setAuthToken(String authToken){
        this.authToken = authToken;
    }

    public List<User> getUserList() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        HttpEntity<User> entity = new HttpEntity<>(headers);
        User[] userList =   restTemplate.exchange(API_BASE_URL, HttpMethod.GET, entity, User[].class).getBody();
        return Arrays.asList(userList);
    }

    public Transfer sendMoney(Transfer transfer) {
        ResponseEntity<BigDecimal> transferAmount;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
        Transfer returnedTransfer = null;
        try {
            returnedTransfer  = restTemplate.postForObject(API_BASE_URL, entity, Transfer.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return returnedTransfer;
    }
    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

}
