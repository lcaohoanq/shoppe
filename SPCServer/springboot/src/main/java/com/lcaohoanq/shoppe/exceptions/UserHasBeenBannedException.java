package com.lcaohoanq.shoppe.exceptions;

public class UserHasBeenBannedException extends RuntimeException {

    private Long id;
    private final String email_phone;

    public UserHasBeenBannedException(String email_phone) {
        super("Account " + email_phone + " has been banned");
        this.email_phone = email_phone;
    }

}
