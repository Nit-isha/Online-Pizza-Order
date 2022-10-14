package com.cg.onlinepizza.exceptions;

public class OrderIdNotFoundException extends Exception {
	public OrderIdNotFoundException() {

	}

	public OrderIdNotFoundException(String message) {
		super(message);
	}
}
