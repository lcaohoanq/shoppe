package com.lcaohoanq.shoppe.exceptions;

import com.lcaohoanq.shoppe.exceptions.base.DataAlreadyExistException;

public class CategoryAlreadyExistException extends DataAlreadyExistException {

    public CategoryAlreadyExistException(String message) {
        super(message);
    }

}
