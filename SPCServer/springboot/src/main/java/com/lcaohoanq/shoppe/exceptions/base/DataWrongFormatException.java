package com.lcaohoanq.shoppe.exceptions.base;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DataWrongFormatException extends RuntimeException{

    public DataWrongFormatException(String message) {
        super(message);
    }

}
