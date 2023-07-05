package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

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
//        SqlRowSet rowSet = jd
        try {
            SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, userId);
            if (rows.next()){
                userAccount = mapRowToAccount(rows);
            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new CannotGetJdbcConnectionException("Unable to Connect");
        }
        return userAccount;
    }

    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setUserId(rs.getInt("user_id"));
        account.setAccountId(rs.getInt("account_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        return account;
    }
}
