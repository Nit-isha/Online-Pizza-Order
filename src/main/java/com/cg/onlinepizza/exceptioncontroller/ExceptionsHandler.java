package com.cg.onlinepizza.exceptioncontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cg.onlinepizza.exceptions.PizzaIdNotFoundException;

@RestControllerAdvice // acts as a catch block
public class ExceptionsHandler {

	@ExceptionHandler(PizzaIdNotFoundException.class)
	public ResponseEntity<APIError> pizzaIdNotFoundExceptionHandler(Exception e) {
		APIError error = new APIError("Pizza not found", 404);
		return new ResponseEntity<APIError>(error, HttpStatus.NOT_FOUND);
	}
}
