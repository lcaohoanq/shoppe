package com.lcaohoanq.shoppe.exception;

import com.lcaohoanq.shoppe.base.exception.DataNotFoundException;

public class RoleNotFoundException extends DataNotFoundException {

    public RoleNotFoundException(String message) {
        super(message);
    }

}
