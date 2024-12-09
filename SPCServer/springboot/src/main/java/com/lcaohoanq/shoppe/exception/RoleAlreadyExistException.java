package com.lcaohoanq.shoppe.exception;

import com.lcaohoanq.shoppe.base.exception.DataAlreadyExistException;

public class RoleAlreadyExistException extends DataAlreadyExistException {

    public RoleAlreadyExistException(String message) {
        super(message);
    }

}
