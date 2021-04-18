package com.github.thiagodutra.coopvoteservice.domain.service.votingsession;

import com.github.thiagodutra.coopvoteservice.domain.repository.VotingSessionRepository;
import com.github.thiagodutra.coopvoteservice.domain.service.VotingSessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VotingSessionServiceImpl implements VotingSessionService{

    @Autowired
    VotingSessionRepository votingSessionRepository;

    
    
}
