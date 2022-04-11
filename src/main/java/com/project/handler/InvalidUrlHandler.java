package com.project.handler;

import com.project.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class InvalidUrlHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public ModelAndView notFound(HttpServletRequest request, ResourceNotFoundException nfe) {
        request.getHeader("Page not found");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("problem", nfe.getMessage());
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
