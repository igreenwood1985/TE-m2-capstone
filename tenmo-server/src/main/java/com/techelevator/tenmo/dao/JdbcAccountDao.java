package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account getUserAccount(int userId) {
        String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?";
        Account userAccount = null;

        try {
            SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, userId);
            if (rows.next()){
                userAccount = mapRowToAccount(rows);
            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to Connect", e);
        }
        return userAccount;
    }
    @Override
    public Account updateAccountBalance(int userId, BigDecimal amount){
        String sql = "UPDATE account SET balance = ? WHERE user_id = ?";
        Account updatedAccount = null;
        int rowsUpdated;
        try {
            rowsUpdated = jdbcTemplate.update(sql, amount, userId);
            updatedAccount = getUserAccount(userId);
            if (rowsUpdated != 1){
                throw new DaoException("Update failed, wrong number of rows returned.");
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database.", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Action would violate data integrity.", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("Invalid syntax.", e);
        }
        return updatedAccount;
    }



    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setUserId(rs.getInt("user_id"));
        account.setAccountId(rs.getInt("account_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        return account;
    }
}
