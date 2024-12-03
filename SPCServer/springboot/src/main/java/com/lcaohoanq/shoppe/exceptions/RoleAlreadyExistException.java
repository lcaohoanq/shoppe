package com.lcaohoanq.shoppe.exceptions;

import com.lcaohoanq.shoppe.exceptions.base.DataAlreadyExistException;

public class RoleAlreadyExistException extends DataAlreadyExistException {

    public RoleAlreadyExistException(String message) {
        super(message);
    }

}
