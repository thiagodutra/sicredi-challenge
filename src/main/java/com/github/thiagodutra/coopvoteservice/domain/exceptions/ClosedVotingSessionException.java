package com.github.thiagodutra.coopvoteservice.domain.exceptions;

public class ClosedVotingSessionException extends RuntimeException{

    public ClosedVotingSessionException(String message){
        super(message);
    }   
}