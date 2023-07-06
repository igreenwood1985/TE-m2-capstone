package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDao  {
    Account getUserAccount(int userId);
    Account updateAccountBalance(int userId, BigDecimal newBalance);


}
