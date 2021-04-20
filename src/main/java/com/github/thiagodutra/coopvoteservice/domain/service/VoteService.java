package com.github.thiagodutra.coopvoteservice.domain.service;

import com.github.thiagodutra.coopvoteservice.domain.dto.VoteDTO;

public interface VoteService {
    

    VoteDTO processVote(Long votingSessionId, VoteDTO voteDto) throws Exception;


}
