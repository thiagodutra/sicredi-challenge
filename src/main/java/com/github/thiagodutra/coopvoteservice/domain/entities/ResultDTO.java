package com.github.thiagodutra.coopvoteservice.domain.entities;

import com.github.thiagodutra.coopvoteservice.domain.dto.VotingSessionDTO;

import lombok.Data;

@Data
public class ResultDTO {
    
    private VotingSessionDTO votingSession;
    private String result;

}
