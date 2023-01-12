package com.example.demo.exceptionhandler.exception;

public class ItemNotFoundException extends ApplicationException {

    public ItemNotFoundException(String message) {
        super(message);
    }
}
