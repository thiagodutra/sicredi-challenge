package com.github.thiagodutra.coopvoteservice.controllers;

import com.github.thiagodutra.coopvoteservice.domain.dto.VoteDTO;
import com.github.thiagodutra.coopvoteservice.domain.exceptions.ClosedVotingSessionException;
import com.github.thiagodutra.coopvoteservice.domain.exceptions.InvalidDocumentNumberException;
import com.github.thiagodutra.coopvoteservice.domain.exceptions.VoteNotSupportedException;
import com.github.thiagodutra.coopvoteservice.domain.response.DefaultErrorResponse;
import com.github.thiagodutra.coopvoteservice.domain.service.VoteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1")
public class VoteController {

    @Autowired
    VoteService voteService;

    @ExceptionHandler
    public ResponseEntity<DefaultErrorResponse> handleClosedVotingSessionException(ClosedVotingSessionException exception) {
        return new ResponseEntity<>(
                new DefaultErrorResponse("Vote", "The voting session closed before you could vote", exception.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<DefaultErrorResponse> handleInvalidDocumentNumberException(InvalidDocumentNumberException exception) {
        return new ResponseEntity<>(
                new DefaultErrorResponse("Vote", "The voting session closed before you could vote", exception.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<DefaultErrorResponse> handleVoteNotSupportedException(VoteNotSupportedException exception) {
        return new ResponseEntity<>(
                new DefaultErrorResponse("Vote", "Something occurred during the voting operation", exception.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "Method to Vote in a VotingSession topic", 
        notes =  "Coputes a vote to a specific Voting Session, if its openned",
        response = VoteDTO.class)
    @PostMapping("/vote/{sessionId}")
    public VoteDTO vote(@PathVariable Long sessionId, @RequestBody VoteDTO voteDto) throws Exception {
        return voteService.processVote(sessionId, voteDto).mapToDTO();
    }
}