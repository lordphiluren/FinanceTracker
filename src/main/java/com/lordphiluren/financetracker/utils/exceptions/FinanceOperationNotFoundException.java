package com.lordphiluren.financetracker.utils.exceptions;

public class FinanceOperationNotFoundException extends RuntimeException{
    public FinanceOperationNotFoundException(String msg) {
        super(msg);
    }
}
