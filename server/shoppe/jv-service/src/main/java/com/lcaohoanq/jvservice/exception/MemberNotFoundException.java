package com.lcaohoanq.jvservice.exception;

import com.lcaohoanq.jvservice.base.exception.DataNotFoundException;

public class MemberNotFoundException extends DataNotFoundException {

    public MemberNotFoundException(String message) {
        super(message);
    }

}
