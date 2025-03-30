package com.lcaohoanq.jvservice.exception;

import com.lcaohoanq.jvservice.base.exception.DataWrongFormatException;

public class PasswordWrongFormatException extends DataWrongFormatException {

    public PasswordWrongFormatException(String message) {
        super(message);
    }

}
