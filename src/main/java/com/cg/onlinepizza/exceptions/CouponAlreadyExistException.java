package com.cg.onlinepizza.exceptions;

public class CouponAlreadyExistException extends Exception{
	public CouponAlreadyExistException(){
		
	}
	public CouponAlreadyExistException(String msg){
			super(msg);
	}
}
