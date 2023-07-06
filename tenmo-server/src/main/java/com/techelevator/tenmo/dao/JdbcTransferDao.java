package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcTransferDao {
    private JdbcTemplate jdbcTemplate;
    public JdbcTransferDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    // create new transfer record in memory
        // one for sending money
        // another for requesting money

    // insert a new transfer record into database
    // update a transfer record's status
    // change effected account balances


    private Transfer mapRowToTransfer(SqlRowSet rows){

        return null;
    }

}
