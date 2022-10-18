package com.cg.onlinepizza.exceptioncontroller;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cg.onlinepizza.exceptions.CouponAlreadyExistException;
import com.cg.onlinepizza.exceptions.CouponIdNotFoundException;
import com.cg.onlinepizza.exceptions.CustomerAlreadyExistException;
import com.cg.onlinepizza.exceptions.CustomerIdNotFoundException;
import com.cg.onlinepizza.exceptions.InvalidMinCostException;
import com.cg.onlinepizza.exceptions.PizzaAlreadyExistException;
import com.cg.onlinepizza.exceptions.PizzaIdNotFoundException;

@RestControllerAdvice // acts as a catch block
public class ExceptionsHandler {
    
    /*-----------------  Pizza Exceptions  -----------------*/
    
    /*Pizza ID Not Found Exception Handler*/
	@ExceptionHandler(PizzaIdNotFoundException.class)
	public ResponseEntity<APIError> pizzaIdNotFoundExceptionHandler(Exception e) {
		APIError error = new APIError("Pizza NOT Found in Database", 404);
		return new ResponseEntity<APIError>(error, HttpStatus.NOT_FOUND);
	}
	
	/*Invalid Minimum cost Exception Handler*/
	@ExceptionHandler(InvalidMinCostException.class)
	public ResponseEntity<APIError> invalidMinCostExceptionHandler(Exception e){
		APIError error = new APIError("Minimum Cost must be between zero and maximum cost", 400);
		return new ResponseEntity<APIError>(error, HttpStatus.BAD_REQUEST);
	}
	
	/*Pizza Already Exist in Database Exception Handler*/
	@ExceptionHandler(PizzaAlreadyExistException.class)
	public ResponseEntity<APIError> pizzaAlreadyExistExceptionHandler(Exception e){
		APIError error = new APIError("Pizza ID already exist in database", 400);
		return new ResponseEntity<APIError>(error, HttpStatus.BAD_REQUEST);
	}
	
	/*-----------------  Registration Exceptions  -----------------*/
	
	/*Constraint Violation Exception Handler*/
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<APIError> constraintViolationExceptionHandler(Exception e){
		APIError error = new APIError("Invalid Email OR Mobile number", 400);
		return new ResponseEntity<APIError>(error, HttpStatus.BAD_REQUEST);
	}
	
	/*-----------------  Customer Exceptions  -----------------*/
	
	/*Customer Already Exists in Database Exception Handler*/
	@ExceptionHandler(CustomerAlreadyExistException.class)
    public ResponseEntity<APIError> CustomerAlreadyExistExceptionHandler(Exception e){
        APIError error = new APIError("Customer already exist in database", 400);
        return new ResponseEntity<APIError>(error, HttpStatus.BAD_REQUEST);
    }
	
	/*Customer ID Not Found Exception Handler*/
    @ExceptionHandler(CustomerIdNotFoundException.class)
    public ResponseEntity<APIError> CustomerIdNotFoundExceptionHandler(Exception e) {
        APIError error = new APIError("Customer Not Found", 404);
        return new ResponseEntity<APIError>(error, HttpStatus.NOT_FOUND);
    }
    
    /*-----------------  Coupon Exceptions  -----------------*/

    /*Coupon Already Exist Exception Exception Handler*/
    @ExceptionHandler(CouponAlreadyExistException.class)
    public ResponseEntity<APIError> couponAlreadyExistExceptionHandler(Exception e) {
        APIError error = new APIError("Coupon already exist in database", 400);
        return new ResponseEntity<APIError>(error, HttpStatus.BAD_REQUEST);
    }
    
    /*Coupon ID Not Found Exception Handler*/
    @ExceptionHandler(CouponIdNotFoundException.class)
    public ResponseEntity<APIError> CouponIdNotFoundExceptionHandler(Exception e) {
        APIError error = new APIError("Coupon Not Found", 404);
        return new ResponseEntity<APIError>(error, HttpStatus.NOT_FOUND);
	}
}
