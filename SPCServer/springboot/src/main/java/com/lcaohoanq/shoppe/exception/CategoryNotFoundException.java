package com.lcaohoanq.shoppe.exception;

import com.lcaohoanq.shoppe.base.exception.DataNotFoundException;

public class CategoryNotFoundException extends DataNotFoundException {

    public CategoryNotFoundException(String message) {
        super(message);
    }

}
