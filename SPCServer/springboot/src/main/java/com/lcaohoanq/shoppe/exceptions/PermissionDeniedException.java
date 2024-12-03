package com.lcaohoanq.shoppe.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class PermissionDeniedException extends RuntimeException {
    private final String reason;
    private final String path;

    public PermissionDeniedException(String message) {
        super(message);
        this.reason = message;
        this.path = null;
    }

    public PermissionDeniedException(String message, String path) {
        super(message);
        this.reason = message;
        this.path = path;
    }

    public String getReason() {
        return reason;
    }

    public String getPath() {
        return path;
    }
}
