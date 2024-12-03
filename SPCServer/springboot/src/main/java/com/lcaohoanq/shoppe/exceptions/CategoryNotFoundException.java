package com.lcaohoanq.shoppe.exceptions;

import com.lcaohoanq.shoppe.exceptions.base.DataNotFoundException;

public class CategoryNotFoundException extends DataNotFoundException {

    public CategoryNotFoundException(String message) {
        super(message);
    }

}
