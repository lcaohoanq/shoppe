package com.lcaohoanq.jvservice.exception;

import com.lcaohoanq.jvservice.base.exception.DataNotFoundException;

public class CategoryNotFoundException extends DataNotFoundException {

    public CategoryNotFoundException(String message) {
        super(message);
    }

}
