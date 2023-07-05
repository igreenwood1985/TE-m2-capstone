package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

public class JdbcAccountDaoTests extends BaseDaoTests{

    private JdbcAccountDao sut;

    @Before
    public void setup(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcAccountDao(jdbcTemplate);
    }

    @Test
    public void getAccountByIdGivesCorrectAccount(){
        Assert.assertEquals(1001,sut.getUserAccount(1001).getUserId());
        Assert.assertEquals(new BigDecimal("1000.00"), sut.getUserAccount(1001).getBalance());
        Assert.assertEquals(2001, sut.getUserAccount(1001).getAccountId());
    }



}
