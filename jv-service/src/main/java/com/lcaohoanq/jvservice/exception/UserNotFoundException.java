package com.lcaohoanq.jvservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserNotFoundException extends RuntimeException {

    private Long id;
    private String email_phone;

    public UserNotFoundException(Long id) {
        super("Could not find user " + id);
    }

    public UserNotFoundException(String email_phone) {
        super("Could not find user" + email_phone);
        this.email_phone = email_phone;
    }

}
