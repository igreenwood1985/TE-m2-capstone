package com.techelevator.tenmo.model;

import com.techelevator.tenmo.dao.JdbcAccountDao;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


public class Transfer {

    private int transferType;

    private int fromUserId;

    private int toUserId;

    private BigDecimal transferAmount;

    public void setTransferType(int transferType) {
        this.transferType = transferType;
    }

    public int getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(int transferStatus) {
        this.transferStatus = transferStatus;
    }

    private int transferStatus;
    private int transferId;

    /*
    Should be able to derive account numbers from user IDs...
    ...but that requires DAO access.
    given that this is a single account per user situation, it's probably fine, but it doesn't feel right.
     */
    private int transferStatusId;
    // always positive.
    // the maximum here is our account balance.
    // buuuuut we gotta get that from the DAO, I guess.



    public Transfer(){

    }
//    public Transfer(int transferId, int fromUserId, int toUserId, BigDecimal transferAmount, int transferStatusId){
//        // constructing a transfer is sending money.
//        // sooooo what do we do.
//        this.transferId = transferId;
//        this.fromUserId = fromUserId;
//        this.toUserId = toUserId;
//        this.transferAmount = transferAmount;
//        this.transferStatusId = transferStatusId;
//    }
//    public Transfer(int transferType, int fromUserId, int toUserId, BigDecimal transferAmount){
//        // constructing a transfer is sending money.
//        // sooooo what do we do.
//        this.transferType = transferType;
//        this.fromUserId = fromUserId;
//        this.toUserId = toUserId;
//        this.transferAmount = transferAmount;
//
//    }

    public int getTransferType() {
        return transferType;
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
