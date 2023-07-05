package com.techelevator.dao;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTests {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @Before
    public void setup(){
        SecurityContextHolder.clearContext();
    }

    @Test
    public void controller_sends_balance_when_asked() {

    }
}
