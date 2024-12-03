package com.lcaohoanq.shoppe.exceptions;

public class JwtAuthenticationException extends RuntimeException {

    public JwtAuthenticationException(String message) {
        super(message);
    }

    public JwtAuthenticationException(String message, Throwable cause) {
      super(message, cause);
    }

}
