package com.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;
import java.io.Serializable;
import java.text.MessageFormat;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException implements Serializable {

   @Serial
   private static final long serialVersionUID = 1L;

   public ResourceNotFoundException(String message) {
      super(message);
   }

   public ResourceNotFoundException(String message, Long argument) {
      super(MessageFormat.format(message, argument));
   }
}
