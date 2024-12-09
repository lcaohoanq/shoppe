package com.lcaohoanq.shoppe.exception;

import com.lcaohoanq.shoppe.base.exception.DataNotFoundException;

public class StaffNotFoundException extends DataNotFoundException {

    public StaffNotFoundException(String message) {
        super(message);
    }

}
