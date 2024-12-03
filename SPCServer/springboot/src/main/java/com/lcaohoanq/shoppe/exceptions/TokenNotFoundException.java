package com.lcaohoanq.shoppe.exceptions;

import com.lcaohoanq.shoppe.exceptions.base.DataNotFoundException;

public class TokenNotFoundException extends DataNotFoundException {

    public TokenNotFoundException(String message) {
        super(message);
    }

}
