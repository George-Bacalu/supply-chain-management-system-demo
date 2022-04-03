package com.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidDataException extends RuntimeException {

   @Serial
   private static final long serialVersionUID = 1L;

   public InvalidDataException(String message) {
      super(message);
   }
}
