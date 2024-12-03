package com.lcaohoanq.shoppe.exceptions;

import com.lcaohoanq.shoppe.exceptions.base.DataNotFoundException;

public class StaffNotFoundException extends DataNotFoundException {

    public StaffNotFoundException(String message) {
        super(message);
    }

}
