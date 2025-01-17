package com.lcaohoanq.shoppe.exception;

public class PhoneAlreadyUsedException extends RuntimeException {

    public PhoneAlreadyUsedException(String message) {
        super(message);
    }
}
