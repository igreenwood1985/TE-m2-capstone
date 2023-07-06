package com.techelevator.tenmo.model;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class Transfer {
    private int transferId;

    private int fromUserId;

    private int toUserId;
    /*
    Should be able to derive account numbers from user IDs...
    ...but that requires DAO access.
    given that this is a single account per user situation, it's probably fine, but it doesn't feel right.
     */
    private int transferStatusId;
    //always positive.
    private BigDecimal transferAmount;


    public Transfer(){

    }
    public Transfer(int transferId, int fromUserId, int toUserId, BigDecimal transferAmount, int transferStatusId, int transferTypeId){
        // constructing a transfer is sending money.
        // sooooo what do we do.
        this.transferId = transferId;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.transferAmount = transferAmount;
        this.transferStatusId = transferStatusId;
    }



    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
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

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "transferId=" + transferId +
                ", fromUserId=" + fromUserId +
                ", toUserId=" + toUserId +
                ", transferStatusId=" + transferStatusId +
                ", transferAmount=" + transferAmount +
                '}';
    }
}
