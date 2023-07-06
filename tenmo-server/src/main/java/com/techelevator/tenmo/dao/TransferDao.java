package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;

public interface TransferDao {
    Transfer sendMoney(BigDecimal amount, int fromUserID, int toUserId);
    Transfer requestMoney(BigDecimal amount, int fromUserId, int toUserId);
}
