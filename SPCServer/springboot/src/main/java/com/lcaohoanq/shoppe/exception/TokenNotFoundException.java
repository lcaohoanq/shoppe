package com.lcaohoanq.shoppe.exception;

import com.lcaohoanq.shoppe.base.exception.DataNotFoundException;

public class TokenNotFoundException extends DataNotFoundException {

    public TokenNotFoundException(String message) {
        super(message);
    }

}
