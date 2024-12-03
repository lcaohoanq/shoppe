package com.lcaohoanq.shoppe.exceptions;

import com.lcaohoanq.shoppe.exceptions.base.DataNotFoundException;

public class MemberNotFoundException extends DataNotFoundException {

    public MemberNotFoundException(String message) {
        super(message);
    }

}
