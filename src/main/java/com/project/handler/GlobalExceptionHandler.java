package com.project.handler;

import com.project.exception.InvalidDataException;
import com.project.exception.InvalidOrderException;
import com.project.exception.InvalidProductException;
import com.project.exception.OrderNotFoundException;
import com.project.exception.ProductNotFoundException;
import com.project.exception.ResourceNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

   @ExceptionHandler(ResourceNotFoundException.class)
   @ResponseStatus(HttpStatus.NOT_FOUND)
   public ResponseEntity<String> resourceNotFoundExceptionHandler(HttpServletRequest request, Exception ex) {
      request.getHeader("Data Not Found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("Status: Data not found - " + ex.getMessage());
   }

   @ExceptionHandler(InvalidDataException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   public ResponseEntity<String> invalidDataExceptionHandler(HttpServletRequest request, Exception ex) {
      request.getHeader("Method Not Allowed");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body("Status: Invalid passed data: " + ex.getMessage());
   }

   @ExceptionHandler(ProductNotFoundException.class)
   @ResponseStatus(HttpStatus.NOT_FOUND)
   public ResponseEntity<String> productNotFoundExceptionHandler(HttpServletRequest request, Exception ex) {
      request.getHeader("Products not found");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body("Status: Products not found: " + ex.getMessage());
   }

   @ExceptionHandler(InvalidProductException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   public ResponseEntity<String> invalidProductExceptionHandler(HttpServletRequest request, Exception ex) {
      request.getHeader("Invalid request of retrieving products");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body("Status: Invalid request of retrieving products: " + ex.getMessage());
   }

   @ExceptionHandler(OrderNotFoundException.class)
   @ResponseStatus(HttpStatus.NOT_FOUND)
   public ResponseEntity<String> orderNotFoundExceptionHandler(HttpServletRequest request, Exception ex) {
      request.getHeader("Orders not found");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body("Status: Orders not found: " + ex.getMessage());
   }

   @ExceptionHandler(InvalidOrderException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   public ResponseEntity<String> invalidOrderExceptionHandler(HttpServletRequest request, Exception ex) {
      request.getHeader("Invalid request of retrieving orders");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body("Status: Invalid request of retrieving orders: " + ex.getMessage());
   }
}
