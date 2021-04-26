package com.github.thiagodutra.coopvoteservice.domain.exceptions;

public class MandatoryAttributeException extends RuntimeException{
    
    public MandatoryAttributeException(String message){
        super(message);
    }
    
}
