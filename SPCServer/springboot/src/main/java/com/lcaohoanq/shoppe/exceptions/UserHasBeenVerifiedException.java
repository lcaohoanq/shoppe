package com.lcaohoanq.shoppe.exceptions;

public class UserHasBeenVerifiedException extends RuntimeException {

    public UserHasBeenVerifiedException(String message) {
        super(message);
    }
}
