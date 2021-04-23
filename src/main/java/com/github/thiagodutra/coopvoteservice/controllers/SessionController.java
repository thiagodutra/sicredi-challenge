package com.github.thiagodutra.coopvoteservice.controllers;

import java.util.NoSuchElementException;

import com.github.thiagodutra.coopvoteservice.domain.dto.VotingSessionDTO;
import com.github.thiagodutra.coopvoteservice.domain.response.DefaultErrorResponse;
import com.github.thiagodutra.coopvoteservice.domain.service.VotingSessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1")
public class SessionController {

    @Autowired
    VotingSessionService votingSessionService;


    @ExceptionHandler
    public ResponseEntity<DefaultErrorResponse> handleNoSuchElementException(NoSuchElementException exception){
        return new ResponseEntity<>(
                new DefaultErrorResponse("VotingSession", "Cannot find the element with the given id", exception.getMessage()),
                HttpStatus.NOT_FOUND);
    }
    @ApiOperation(value = "Method to create a Voting Session in Database", response = VotingSessionDTO.class)
    @PostMapping("/session")
    public ResponseEntity<VotingSessionDTO> create(@RequestBody VotingSessionDTO votingSessionDTO) {
        return ResponseEntity.ok(votingSessionService.save(votingSessionDTO).mapToDTO());
    }
    @ApiOperation(value = "Method to retrieve a VotingSession in Database", response = VotingSessionDTO.class)
    @GetMapping("/session/{id}")
    public ResponseEntity<VotingSessionDTO> findById(@PathVariable Long votingSessionId) {
        return ResponseEntity.ok(votingSessionService.findById(votingSessionId).mapToDTO());
    }
}