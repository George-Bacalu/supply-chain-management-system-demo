package com.project.exception;

import com.project.entity.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;
import java.io.Serializable;
import java.text.MessageFormat;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ProductException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public ProductException(String message) {
        super(message);
    }
}
