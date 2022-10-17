package com.cg.onlinepizza.exceptioncontroller;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cg.onlinepizza.exceptions.InvalidMinCostException;
import com.cg.onlinepizza.exceptions.PizzaAlreadyExistException;
import com.cg.onlinepizza.exceptions.PizzaIdNotFoundException;

@RestControllerAdvice // acts as a catch block
public class ExceptionsHandler {

	@ExceptionHandler(PizzaIdNotFoundException.class)
	public ResponseEntity<APIError> pizzaIdNotFoundExceptionHandler(Exception e) {
		APIError error = new APIError("Pizza not found", 404);
		return new ResponseEntity<APIError>(error, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(InvalidMinCostException.class)
	public ResponseEntity<APIError> invalidMinCostExceptionHandler(Exception e){
		APIError error = new APIError("Minimum Cost must be between zero and maximum cost", 400);
		return new ResponseEntity<APIError>(error, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(PizzaAlreadyExistException.class)
	public ResponseEntity<APIError> pizzaAlreadyExistExceptionHandler(Exception e){
		APIError error = new APIError("Pizza ID already exist in database", 400);
		return new ResponseEntity<APIError>(error, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<APIError> constraintViolationExceptionHandler(Exception e){
		APIError error = new APIError("Invalid details", 400);
		return new ResponseEntity<APIError>(error, HttpStatus.BAD_REQUEST);
	}
	
}
