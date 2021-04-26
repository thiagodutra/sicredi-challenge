package com.github.thiagodutra.coopvoteservice.domain.service;

import com.github.thiagodutra.coopvoteservice.domain.dto.VoteDTO;
import com.github.thiagodutra.coopvoteservice.domain.entities.Vote;

public interface VoteService {
    

    Vote processVote(Long votingSessionId, VoteDTO voteDto);


}
