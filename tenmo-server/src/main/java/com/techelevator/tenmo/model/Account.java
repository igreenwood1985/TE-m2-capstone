package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {
    private BigDecimal balance;

   private int userId;
    private int accountId;

    public BigDecimal getBalance() {
        return balance;
    }

    public Account(){

    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }


    public void increaseBalance(BigDecimal amount){
        BigDecimal newBalance = this.getBalance().add(amount);
        this.setBalance(newBalance);
    }

    public void decreaseBalance(BigDecimal amount){
        BigDecimal newBalance = this.getBalance().add(amount);
        this.setBalance(newBalance);
    }

    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance +
                ", userId=" + userId +
                ", accountId=" + accountId +
                '}';
    }
}
