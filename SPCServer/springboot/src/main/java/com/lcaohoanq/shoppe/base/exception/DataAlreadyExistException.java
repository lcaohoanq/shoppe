package com.lcaohoanq.shoppe.base.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DataAlreadyExistException extends RuntimeException{

    public DataAlreadyExistException(String message) {
        super(message);
    }

}
