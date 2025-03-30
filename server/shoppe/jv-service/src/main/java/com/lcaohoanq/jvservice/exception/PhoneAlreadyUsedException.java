package com.lcaohoanq.jvservice.exception;

public class PhoneAlreadyUsedException extends RuntimeException {

    public PhoneAlreadyUsedException(String message) {
        super(message);
    }
}
