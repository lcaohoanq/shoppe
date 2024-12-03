package com.lcaohoanq.shoppe.exceptions;

import com.lcaohoanq.shoppe.exceptions.base.DataNotFoundException;

public class BreederNotFoundException extends DataNotFoundException {

    public BreederNotFoundException(String message) {
        super(message);
    }

}
