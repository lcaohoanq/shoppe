package com.lcaohoanq.shoppe.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GenerateDataException extends RuntimeException {
    public GenerateDataException(String message) {
        super(message);
    }
}
