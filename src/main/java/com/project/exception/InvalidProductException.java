package com.project.exception;

import com.project.entity.Order;
import com.project.entity.Product;

import java.io.Serial;
import java.io.Serializable;
import java.text.MessageFormat;

public class InvalidProductException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidProductException(String message, Order order) {
        super(MessageFormat.format(message, order));
    }

    public InvalidProductException(String message, Product product) {
        super(MessageFormat.format(message, product));
    }

    public InvalidProductException(String message, Long id) {
        super(MessageFormat.format(message, id));
    }
}
