package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.BitSet;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {
    // receive incoming transfer requests, and accept/reject transfers accordingly.
    // should the transfer type be determined by URL parameters, request body, or something else?
    // status is determined by transfer types

    private JdbcAccountDao accountDao;
    private JdbcTransferDao transferDao;
    private JdbcUserDao userDao;

    public TransferController(JdbcTransferDao transferDao, JdbcAccountDao accountDao, JdbcUserDao userDao){
        this.accountDao = accountDao;
        this.transferDao = transferDao;
        this. userDao = userDao;
    }
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/transfer", method = RequestMethod.POST)
    public Transfer createTransfer(@RequestBody Transfer transfer, Principal principal){
        // TODO: add validation to check transfer amount against from account balance
        // select correct DAO method based on transferType
        if(!userDao.findByUsername(principal.getName()).equals(transfer.getFromUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "YOU CAN'T DO THAT 👏 👏 👏👏👏");
        }
        try {
            if (transfer.getTransferType() == 2){
                // check if current user's balance is high enough to accommodate amount
                // check if from and to aren't the same person
                // check that our users actually exist


                if(transfer.getTransferAmount().compareTo(accountDao.getUserAccount(transfer.getFromUserId()).getBalance()) == -1
                && transfer.getFromUserId()  != transfer.getToUserId()) {

                    // call sendMoney if we have enough money to send
                    Account fromAccount = accountDao.getUserAccount(transfer.getFromUserId());
                    Account toAccount = accountDao.getUserAccount(transfer.getToUserId());
                    transferDao.sendMoney(transfer.getTransferAmount(), transfer.getFromUserId(), transfer.getToUserId());

                    // pull accounts for our users and adjust the balances
                    fromAccount.decreaseBalance(transfer.getTransferAmount());
                    toAccount.increaseBalance(transfer.getTransferAmount());

                    // update accounts on the DB
                    accountDao.updateAccountBalance(transfer.getFromUserId(), fromAccount.getBalance());
                    accountDao.updateAccountBalance(transfer.getToUserId(), toAccount.getBalance());

                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "YOU CAN'T DO THAT 👏 👏 👏👏👏");
                }
            } else {
                //call requestMoney
                transferDao.requestMoney(transfer.getTransferAmount(), transfer.getFromUserId(), transfer.getToUserId());
            }
        } catch (ResourceAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Couldn't get connected for some reason");
        }
        return transfer;
    }


}
