package com.lcaohoanq.jvservice.exception;

import com.lcaohoanq.jvservice.base.exception.DataNotFoundException;

public class TokenNotFoundException extends DataNotFoundException {

    public TokenNotFoundException(String message) {
        super(message);
    }

}
