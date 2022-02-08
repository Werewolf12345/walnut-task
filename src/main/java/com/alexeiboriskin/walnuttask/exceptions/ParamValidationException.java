package com.alexeiboriskin.walnuttask.exceptions;

public class ParamValidationException extends RuntimeException {

    public ParamValidationException(String message) {
        super(message);
    }
}
