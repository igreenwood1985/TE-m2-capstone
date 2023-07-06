package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {
    private int transferType;
    private int fromUserId;
    private int toUserId;
    private BigDecimal transferAmount;

    public Transfer() {

    }
    public Transfer(int transferType, int fromUserId, int toUserId, BigDecimal amount) {
        this.transferType = transferType;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.transferAmount = amount;
    }

    public int getTransferType() {
        return transferType;
    }

    public void setTransferType(int transferType) {
        this.transferType = transferType;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }
}
