package com.github.thiagodutra.coopvoteservice.domain.service;

import com.github.thiagodutra.coopvoteservice.domain.dto.VotingSessionDTO;

public interface VotingSessionService {

    VotingSessionDTO save(VotingSessionDTO votingSessionDTO);
    VotingSessionDTO findById(Long id);

}