package com.github.thiagodutra.coopvoteservice.domain.service;

import java.time.LocalDateTime;
import java.util.List;

import com.github.thiagodutra.coopvoteservice.domain.dto.VotingSessionDTO;
import com.github.thiagodutra.coopvoteservice.domain.entities.VotingSession;

public interface VotingSessionService {

    VotingSessionDTO save(VotingSessionDTO votingSessionDTO);
    VotingSessionDTO findById(Long id);
    List<VotingSession> findClosedSessions(LocalDateTime actualTime);

}