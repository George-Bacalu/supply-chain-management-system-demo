package com.project.exception;

import com.project.entity.Order;
import com.project.entity.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;
import java.io.Serializable;
import java.text.MessageFormat;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidDataException extends RuntimeException implements Serializable {

   @Serial
   private static final long serialVersionUID = 1L;

   public InvalidDataException(String message, Product product) {
      super(MessageFormat.format(message, product));
   }

   public InvalidDataException(String message, Order order) {
      super(MessageFormat.format(message, order));
   }

   public InvalidDataException(String message, Long id) {
      super(MessageFormat.format(message, id));
   }
}
