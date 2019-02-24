package com.softbank.recipesitory.controller;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.annotation.JsonView;
import com.softbank.recipesitory.exception.RecipeNotFoundException;
import com.softbank.recipesitory.models.ResponseViews;
import com.softbank.recipesitory.models.ErrorResponse;
import com.softbank.recipesitory.models.Messages;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<Object>(new ErrorResponse(Messages.CREATION_FAILED, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
   
    @JsonView(ResponseViews.MessageOnly.class)
	@ExceptionHandler(RecipeNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse recipeNotFoundHandler(RecipeNotFoundException ex) {
    	System.out.println("bglobal");
		return new ErrorResponse(Messages.NOT_FOUND, "");
	}

   @ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> defaultErrorHandler(Exception ex) {
		return new ResponseEntity<>(new ErrorResponse(Messages.CREATION_FAILED, ex.getMessage()),
									HttpStatus.BAD_REQUEST);
	}
   
   @ExceptionHandler({ ConstraintViolationException.class })
   public ResponseEntity<Object> handleConstraintViolation(
     ConstraintViolationException ex, WebRequest request) {
       StringBuilder errors = new StringBuilder();
       for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
           errors.append(violation.getRootBeanClass().getName() + " " + 
             violation.getPropertyPath() + ": " + violation.getMessage());
       }
    
       return new ResponseEntity<Object>(
         new ErrorResponse(Messages.CREATION_FAILED, errors.toString()), HttpStatus.BAD_REQUEST);
   }

}