package com.project.exception;

import com.project.entity.Order;

import java.io.Serial;
import java.io.Serializable;
import java.text.MessageFormat;

public class InvalidOrderException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidOrderException(String message, Order order) {
        super(MessageFormat.format(message, order));
    }

    public InvalidOrderException(String message, Long id) {
        super(MessageFormat.format(message, id));
    }
}
