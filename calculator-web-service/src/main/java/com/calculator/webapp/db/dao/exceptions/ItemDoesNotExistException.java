package com.calculator.webapp.db.dao.exceptions;

public class ItemDoesNotExistException extends Exception {
    public ItemDoesNotExistException(final String message){
        super(message);
    }
}
