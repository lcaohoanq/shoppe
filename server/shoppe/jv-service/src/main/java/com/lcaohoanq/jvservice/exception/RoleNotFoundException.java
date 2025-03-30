package com.lcaohoanq.jvservice.exception;

import com.lcaohoanq.jvservice.base.exception.DataNotFoundException;

public class RoleNotFoundException extends DataNotFoundException {

    public RoleNotFoundException(String message) {
        super(message);
    }

}
