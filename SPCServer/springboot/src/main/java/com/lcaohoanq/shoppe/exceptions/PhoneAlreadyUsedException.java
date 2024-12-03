package com.lcaohoanq.shoppe.exceptions;

public class PhoneAlreadyUsedException extends RuntimeException {

    public PhoneAlreadyUsedException(String message) {
        super(message);
    }
}
