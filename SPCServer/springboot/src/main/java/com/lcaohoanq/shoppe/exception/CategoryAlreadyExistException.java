package com.lcaohoanq.shoppe.exception;

import com.lcaohoanq.shoppe.base.exception.DataAlreadyExistException;

public class CategoryAlreadyExistException extends DataAlreadyExistException {

    public CategoryAlreadyExistException(String message) {
        super(message);
    }

}
