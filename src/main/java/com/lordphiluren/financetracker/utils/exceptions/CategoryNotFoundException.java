package com.lordphiluren.financetracker.utils.exceptions;

import com.lordphiluren.financetracker.models.Category;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(String msg){
        super(msg);
    }
}
