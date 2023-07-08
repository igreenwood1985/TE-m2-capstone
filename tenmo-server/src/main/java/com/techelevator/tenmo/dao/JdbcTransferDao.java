package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {
    private JdbcTemplate jdbcTemplate;
    public JdbcTransferDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    // transfers need account IDs, but we only care about user IDs.
    // for all transfers, we'll get our from user ID off the principle, and the to user ID off their selection (in the client).
    // the amounts should come from the client as well.
    // we'll need to validate the incoming requests, though:
    // selected users need to be real.
    // request amounts and targets need to be valid - can't send more than what's in your account, can't send to yourself, can't request from yourself.
    //

    public List<Transfer> getAllTransfersForUser(int userId){
        List<Transfer> transfers = new ArrayList<Transfer>();
        String sql = "SELECT transfer_id, transfer_status_id , transfer_type_id, from_account.user_id AS from_user_id, to_account.user_id AS to_user_id, amount " +
                "                FROM transfer AS t " +
                "                JOIN account as from_account ON t.account_from = from_account.account_id " +
                "                JOIN account as to_account ON t.account_to = to_account.account_id " +
                "                WHERE from_account.user_id = ? OR to_account.user_id = ?;";
        try{
            SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, userId, userId);
            while(rows.next()){
                transfers.add(mapRowToTransfer(rows));
            }

        } catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to the database.", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Action would violate data integrity.", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("Invalid syntax.", e);
        }

        return transfers;
    }


    public Transfer getTransferById(int transferId){
        String sql = "SELECT transfer_id, transfer_status_id , transfer_type_id, from_account.user_id AS from_user_id, to_account.user_id AS to_user_id, amount " +
                "                FROM transfer AS t " +
                "                JOIN account as from_account ON t.account_from = from_account.account_id " +
                "                JOIN account as to_account ON t.account_to = to_account.account_id " +
                "                WHERE transfer_id = ?;";

        Transfer returnedTransfer = null;

        try {
            SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, transferId);
                if(rows.next()){
                    returnedTransfer = mapRowToTransfer(rows);
                } else {
                    throw new DaoException("Transfer not found.");
                }

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database.", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Action would violate data integrity.", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("Invalid syntax.", e);
        }


        return returnedTransfer;
    }
    @Override
    public Transfer sendMoney(BigDecimal amount, int fromUserId, int toUserId){
        // sends are instantly approved (status 2)
        // sends are type 2
        Transfer transfer = null;
        int transferId;
        // create the transfer
        String sql = "INSERT INTO transfer (transfer_status_id, transfer_type_id, account_from, account_to, amount) " +
                "VALUES (2, 2, (SELECT account_id FROM account WHERE user_id = ?), (SELECT account_id FROM account WHERE user_id = ?), ?) RETURNING transfer_id;";

        // update account balances
        // decrease the sender's account



        try {
           transferId = jdbcTemplate.queryForObject(sql, int.class, fromUserId, toUserId, amount);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database.", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Action would violate data integrity.", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("Invalid syntax.", e);
        }
        transfer = new Transfer();
        transfer.setTransferId(transferId);
        transfer.setToUserId(toUserId);
        transfer.setFromUserId(fromUserId);
        transfer.setTransferAmount(amount);

        return transfer;
    }

    @Override
    public Transfer requestMoney(BigDecimal amount, int fromUserId, int toUserId) {
        // sends are instantly approved (status 2)
        // sends are type 2
        Transfer transfer = null;
        int transferId;
        String sql = "INSERT INTO transfer (transfer_status_id, transfer_type_id, account_from, account_to, amount) " +
                "VALUES (2, 2, (SELECT account_id FROM account WHERE user_id = ?), (SELECT account_id FROM account WHERE user_id = ?), ?) RETURNING transfer_id;";
        //
        try {
            transferId = jdbcTemplate.queryForObject(sql, int.class, fromUserId, toUserId, amount);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database.", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Action would violate data integrity.", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("Invalid syntax.", e);
        }

        transfer.setTransferId(transferId);
        transfer.setToUserId(toUserId);
        transfer.setFromUserId(fromUserId);
        transfer.setTransferAmount(amount);

        return transfer;
    }






    private Transfer mapRowToTransfer(SqlRowSet rows){
        Transfer transfer = new Transfer();
        transfer.setTransferId(rows.getInt("transfer_id"));
        transfer.setFromUserId(rows.getInt("from_user_id"));
        transfer.setToUserId(rows.getInt("to_user_id"));
        transfer.setTransferAmount(rows.getBigDecimal("amount"));
        transfer.setTransferType(rows.getInt("transfer_type_id"));
        transfer.setTransferStatus(rows.getInt("transfer_status_id"));

        return transfer;
    }

}
