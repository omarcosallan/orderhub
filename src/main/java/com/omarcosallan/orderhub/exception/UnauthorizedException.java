package com.omarcosallan.orderhub.exception;

public class UnauthorizedException extends RuntimeException{

    public UnauthorizedException(String message) {
        super(message);
    }
}
