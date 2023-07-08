package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
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

    public Transfer getTransferById (int id){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        Transfer retrievedTransfer = restTemplate.exchange(API_BASE_URL + "s/" + id, HttpMethod.GET, entity, Transfer.class ).getBody();

        return retrievedTransfer;
    }

    public List<Transfer> getUserTransfers(){
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(authToken);
    HttpEntity<Void>entity = new HttpEntity<>(headers);
    Transfer[] userTransfers = restTemplate.exchange(API_BASE_URL + "s", HttpMethod.GET, entity, Transfer[].class).getBody();
    List<Transfer>userTransferList = Arrays.asList(userTransfers);
    return userTransferList;
    }
    public List<User> getUserNameList() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        HttpEntity<User> entity = new HttpEntity<>(headers);
        User[] users = restTemplate.exchange(API_BASE_URL, HttpMethod.GET, entity, User[].class).getBody();
        List<User> userList = Arrays.asList(users);
        return userList;
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
