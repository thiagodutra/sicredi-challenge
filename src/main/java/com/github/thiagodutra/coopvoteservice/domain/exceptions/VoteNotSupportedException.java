package com.github.thiagodutra.coopvoteservice.domain.exceptions;

public class VoteNotSupportedException extends RuntimeException{
    
    public VoteNotSupportedException(String message){
        super(message);
    }

}
