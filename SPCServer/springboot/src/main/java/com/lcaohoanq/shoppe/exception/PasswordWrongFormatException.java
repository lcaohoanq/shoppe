package com.lcaohoanq.shoppe.exception;

import com.lcaohoanq.shoppe.base.exception.DataWrongFormatException;

public class PasswordWrongFormatException extends DataWrongFormatException {

    public PasswordWrongFormatException(String message) {
        super(message);
    }

}
