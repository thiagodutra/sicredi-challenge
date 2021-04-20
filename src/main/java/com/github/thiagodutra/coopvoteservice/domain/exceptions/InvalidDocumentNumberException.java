package com.github.thiagodutra.coopvoteservice.domain.exceptions;

public class InvalidDocumentNumberException extends RuntimeException{
    
    public InvalidDocumentNumberException(String message){
        super(message);
    }
}
