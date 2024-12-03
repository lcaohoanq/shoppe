package com.lcaohoanq.shoppe.exceptions.base;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DataAlreadyExistException extends RuntimeException{

    public DataAlreadyExistException(String message) {
        super(message);
    }

}
