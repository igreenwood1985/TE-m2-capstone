package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

    private final UserDao userDao;
    private final AccountDao accountDao;

    //private final String API_BASE_URL = "http://localhost:8080/account";
    public AccountController(UserDao userDao, AccountDao accountDao) {
        this.userDao = userDao;
        this.accountDao = accountDao;
    }

    @RequestMapping(path = "/account", method = RequestMethod.GET)
    public BigDecimal getUserAccountBalance(Principal user){
        int userId = userDao.findIdByUsername(user.getName());
        BigDecimal userBalance = accountDao.getUserAccount(userId).getBalance();

        return userBalance;

    }
}
