package com.techelevator.tenmo.exception;

import javax.sql.DataSource;

public class DaoException extends RuntimeException{
    public DaoException (String message, Throwable cause){
        super(message, cause);
    }
    public DaoException (String message){
        super(message);
    }
    public DaoException(){

    }
}
