package com.lcaohoanq.shoppe.base.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DataWrongFormatException extends RuntimeException{

    public DataWrongFormatException(String message) {
        super(message);
    }

}
