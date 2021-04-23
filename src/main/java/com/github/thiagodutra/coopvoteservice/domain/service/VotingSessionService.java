package com.github.thiagodutra.coopvoteservice.domain.service;

import java.time.LocalDateTime;
import java.util.List;

import com.github.thiagodutra.coopvoteservice.domain.dto.VotingSessionDTO;
import com.github.thiagodutra.coopvoteservice.domain.entities.VotingSession;

public interface VotingSessionService {

    VotingSession save(VotingSessionDTO votingSessionDTO);
    VotingSession findById(Long id);
    List<VotingSession> findClosedSessionsToProcess(LocalDateTime actualTime);

}