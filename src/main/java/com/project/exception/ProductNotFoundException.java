package com.project.exception;

import java.io.Serial;
import java.io.Serializable;
import java.text.MessageFormat;

public class ProductNotFoundException extends RuntimeException implements Serializable {

   @Serial
   private static final long serialVersionUID = 1L;

   public ProductNotFoundException(String message) {
      super(message);
   }

    public ProductNotFoundException(String message, Long id) {
        super(MessageFormat.format(message, id));
    }
}
