package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;

public interface TransferDao {
    /**
     * Sends money from one user's account to another.
     * Since users may only have a single account, we are able to locate account IDs based on user IDs
     * without having to worry about choosing the wrong account.
     * @param amount
     * @param fromUserId
     * @param toUserId
     * @return
     */
    Transfer sendMoney(BigDecimal amount, int fromUserId, int toUserId);

    /**
     * Creates a request for money which must be approved by the recipient.
     *
     * @param amount
     * @param fromUserId
     * @param toUserId
     * @return
     */
    Transfer requestMoney(BigDecimal amount, int fromUserId, int toUserId);
}
