package com.project.controller;

import com.project.exception.InvalidDataException;
import com.project.exception.ResourceNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandler {

   @org.springframework.web.bind.annotation.ExceptionHandler(ResourceNotFoundException.class)
   @ResponseStatus(HttpStatus.NOT_FOUND)
   public ResponseEntity<String> resourceNotFoundExceptionHandler(HttpServletRequest request, Exception ex) {
      request.getHeader("Data Not Found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("Status: Data not found - " + ex.getMessage());
   }

   @org.springframework.web.bind.annotation.ExceptionHandler(InvalidDataException.class)
   @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
   public ResponseEntity<String> invalidDataExceptionHandler(HttpServletRequest request, Exception ex) {
      request.getHeader("Method Not Allowed");
      return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).contentType(MediaType.APPLICATION_JSON).body("Status: Invalid passed data: " + ex.getMessage());
   }
}
