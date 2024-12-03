package com.lcaohoanq.shoppe.exceptions;

import com.lcaohoanq.shoppe.exceptions.base.DataWrongFormatException;

public class PasswordWrongFormatException extends DataWrongFormatException {

    public PasswordWrongFormatException(String message) {
        super(message);
    }

}
