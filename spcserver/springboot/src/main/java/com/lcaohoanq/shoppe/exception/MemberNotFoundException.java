package com.lcaohoanq.shoppe.exception;

import com.lcaohoanq.shoppe.base.exception.DataNotFoundException;

public class MemberNotFoundException extends DataNotFoundException {

    public MemberNotFoundException(String message) {
        super(message);
    }

}
