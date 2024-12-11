package com.cg.onlinepizza.exceptions;

public class NoOrdersFoundException extends Exception {
	public NoOrdersFoundException() {

	}

	public NoOrdersFoundException(String message) {
		super(message);
	}
}
