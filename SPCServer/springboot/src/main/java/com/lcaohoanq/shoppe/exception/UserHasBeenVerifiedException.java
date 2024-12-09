package com.lcaohoanq.shoppe.exception;

public class UserHasBeenVerifiedException extends RuntimeException {

    public UserHasBeenVerifiedException(String message) {
        super(message);
    }
}
