package com.project.exception;

import java.io.Serial;
import java.io.Serializable;
import java.text.MessageFormat;

public class ResourceNotFoundException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Long id) {
        super(MessageFormat.format(message, id));
    }
}
