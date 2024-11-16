package com.prdtech.com.usermanagement.exceptionHandling;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
