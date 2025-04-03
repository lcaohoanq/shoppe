package com.lcaohoanq.jvservice.exception;

import com.lcaohoanq.jvservice.base.exception.DataNotFoundException;

public class StaffNotFoundException extends DataNotFoundException {

    public StaffNotFoundException(String message) {
        super(message);
    }

}
