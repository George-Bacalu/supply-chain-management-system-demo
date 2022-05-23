package com.project.exception;

import java.io.Serial;
import java.io.Serializable;

public class InvalidDataException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}
