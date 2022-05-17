package com.project.exception;

import java.io.Serial;
import java.io.Serializable;
import java.text.MessageFormat;

public class OrderNotFoundException extends RuntimeException implements Serializable {

   @Serial
   private static final long serialVersionUID = 1L;

   public OrderNotFoundException(String message) {
      super(message);
   }

    public OrderNotFoundException(String message, Long id) {
        super(MessageFormat.format(message, id));
    }
}
