package com.lcaohoanq.jvservice.exception;

import com.lcaohoanq.jvservice.base.exception.DataAlreadyExistException;

public class CategoryAlreadyExistException extends DataAlreadyExistException {

    public CategoryAlreadyExistException(String message) {
        super(message);
    }

}
