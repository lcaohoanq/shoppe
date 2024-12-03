package com.lcaohoanq.shoppe.exceptions;

import com.lcaohoanq.shoppe.exceptions.base.DataNotFoundException;

public class RoleNotFoundException extends DataNotFoundException {

    public RoleNotFoundException(String message) {
        super(message);
    }

}
