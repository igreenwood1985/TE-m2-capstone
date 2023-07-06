package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {
    // receive incoming transfer requests, and accept/reject transfers accordingly.
    // should the transfer type be determined by URL parameters, request body, or something else?
    // status is determined by transfer types

    @RequestMapping(path = "/transfer", method = RequestMethod.POST)
    public Transfer createTransfer(@Valid @RequestBody int transferType, @RequestBody int transferStatus, @RequestBody int fromUserId, @RequestBody int toUserId, @RequestBody BigDecimal amount){

        return null;
    }


}
