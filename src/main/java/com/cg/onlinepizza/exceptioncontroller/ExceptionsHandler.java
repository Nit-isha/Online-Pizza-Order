package com.cg.onlinepizza.exceptioncontroller;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.cg.onlinepizza.coupon.service.CouponTypeNotFoundException;
import com.cg.onlinepizza.exceptions.CouponAlreadyExistException;
import com.cg.onlinepizza.exceptions.CouponIdNotFoundException;
import com.cg.onlinepizza.exceptions.CustomerAlreadyExistException;
import com.cg.onlinepizza.exceptions.CustomerIdNotFoundException;
import com.cg.onlinepizza.exceptions.InvalidMinCostException;
import com.cg.onlinepizza.exceptions.NoOrdersFoundException;
import com.cg.onlinepizza.exceptions.OrderCancelDeclinedException;
import com.cg.onlinepizza.exceptions.OrderIdNotFoundException;
import com.cg.onlinepizza.exceptions.OrderUpdateDeclinedException;
import com.cg.onlinepizza.exceptions.PizzaAlreadyExistException;
import com.cg.onlinepizza.exceptions.PizzaIdNotFoundException;
import com.cg.onlinepizza.exceptions.UsernameAlreadyExistException;

import org.postgresql.util.PSQLException;

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
		APIError error = new APIError("Pizza Id or name already exists in database", 400);
		return new ResponseEntity<APIError>(error, HttpStatus.BAD_REQUEST);
	}
	
	/*-----------------  Registration Exceptions  -----------------*/
	
	/*Constraint Violation Exception Handler*/
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<APIError> constraintViolationExceptionHandler(Exception e){
		APIError error = new APIError("Invalid Email OR Mobile number", 400);
		return new ResponseEntity<APIError>(error, HttpStatus.BAD_REQUEST);
	}
	
	/*Username Already Exists in Database Exception Handler*/
	@ExceptionHandler(PSQLException.class)
	public ResponseEntity<APIError> pSQLException(Exception e){
		APIError error = new APIError("This Username is already taken.", 400);
		return new ResponseEntity<APIError>(error, HttpStatus.BAD_REQUEST);
	}
	
	/*-----------------  Customer Exceptions  -----------------*/
	
	/*Customer Already Exists in Database Exception Handler*/
	@ExceptionHandler(CustomerAlreadyExistException.class)
    public ResponseEntity<APIError> customerAlreadyExistExceptionHandler(Exception e){
        APIError error = new APIError("Customer already exist in database", 400);
        return new ResponseEntity<APIError>(error, HttpStatus.BAD_REQUEST);
    }
	
	/*Customer ID Not Found Exception Handler*/
    @ExceptionHandler(CustomerIdNotFoundException.class)
    public ResponseEntity<APIError> customerIdNotFoundExceptionHandler(Exception e) {
        APIError error = new APIError("Customer Not Found", 404);
        return new ResponseEntity<APIError>(error, HttpStatus.NOT_FOUND);
    }
    
    /*User name already taken*/
    @ExceptionHandler(UsernameAlreadyExistException.class)
    public ResponseEntity<APIError> usernameAlreadyExistExceptionHandler(Exception e) {
        APIError error = new APIError("The username is already taken", 404);
        
        return new ResponseEntity<APIError>(error, HttpStatus.BAD_REQUEST);
    }
    
    /*-----------------  Coupon Exceptions  -----------------*/

    /*Coupon Already Exist Exception Exception Handler*/
    @ExceptionHandler(CouponAlreadyExistException.class)
    public ResponseEntity<APIError> couponAlreadyExistExceptionHandler(Exception e) {
        APIError error = new APIError("Coupon already exist in database", 400);
        return new ResponseEntity<APIError>(error, HttpStatus.BAD_REQUEST);
    }
    
    /*Constraint Violation Exception Handler*/
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<APIError> methodArgumentNotValidException(Exception e){
		APIError error = new APIError("Invalid coupon name", 400);
		return new ResponseEntity<APIError>(error, HttpStatus.BAD_REQUEST);
	}
	
    /*Coupon ID Not Found Exception Handler*/
    @ExceptionHandler(CouponIdNotFoundException.class)
    public ResponseEntity<APIError> couponIdNotFoundExceptionHandler(Exception e) {
        APIError error = new APIError("Coupon Not Found", 404);
        return new ResponseEntity<APIError>(error, HttpStatus.NOT_FOUND);
	}
    
    @ExceptionHandler(CouponTypeNotFoundException.class)
    public ResponseEntity<APIError> couponTypeNotFoundExceptionHandler(Exception e) {
        APIError error = new APIError("Coupon Not Found", 404);
        return new ResponseEntity<APIError>(error, HttpStatus.NOT_FOUND);
	}
    
    
    /*-----------------  PizzaOrder Exceptions  -----------------*/
    
    /*Order ID Not Found Exception Handler*/
    @ExceptionHandler(OrderIdNotFoundException.class)
    public ResponseEntity<APIError> orderIdNotFoundExceptionHandler(Exception e) {
        APIError error = new APIError("OrderId Not Found", 404);
        return new ResponseEntity<APIError>(error, HttpStatus.NOT_FOUND);
    }
    
    /*Unable to Cancel Order After 15mins Exception Handler*/
    @ExceptionHandler(OrderCancelDeclinedException.class)
    public ResponseEntity<APIError> orderCancelDeclinedExceptionHandler(Exception e) {
    	APIError error = new APIError("Oops!! Order cannot be cancelled after 15 minutes.", 400);
    	return new ResponseEntity<APIError>(error, HttpStatus.BAD_REQUEST);
    }
    
    /*Unable to Update Order After 15mins Exception Handler*/
    @ExceptionHandler(OrderUpdateDeclinedException.class)
    public ResponseEntity<APIError> orderUpdateDeclinedExceptionHandler(Exception e) {
    	APIError error = new APIError("Oops!! Order cannot be updated after 15 minutes.", 400);
    	return new ResponseEntity<APIError>(error, HttpStatus.BAD_REQUEST);
    }
    
    /*No Orders Found Exception Handler*/
    @ExceptionHandler(NoOrdersFoundException.class)
    public ResponseEntity<APIError> NoOrdersFoundException(Exception e) {
    	APIError error = new APIError("No orders found for current date", 404);
    	return new ResponseEntity<APIError>(error, HttpStatus.NOT_FOUND);
}
}