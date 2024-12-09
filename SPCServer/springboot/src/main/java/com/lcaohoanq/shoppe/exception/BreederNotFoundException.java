package com.lcaohoanq.shoppe.exception;

import com.lcaohoanq.shoppe.base.exception.DataNotFoundException;

public class BreederNotFoundException extends DataNotFoundException {

    public BreederNotFoundException(String message) {
        super(message);
    }

}
