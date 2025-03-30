package com.lcaohoanq.jvservice.exception;

import com.lcaohoanq.jvservice.base.exception.DataAlreadyExistException;

public class RoleAlreadyExistException extends DataAlreadyExistException {

    public RoleAlreadyExistException(String message) {
        super(message);
    }

}
