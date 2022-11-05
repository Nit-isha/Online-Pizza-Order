package com.cg.onlinepizza.exceptions;

public class UsernameAlreadyExistException extends Exception{
    public UsernameAlreadyExistException() {
        
    }
    
    public UsernameAlreadyExistException(String message) {
        super(message);
    }
}
