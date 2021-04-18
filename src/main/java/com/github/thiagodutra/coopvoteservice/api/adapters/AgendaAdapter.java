package com.github.thiagodutra.coopvoteservice.api.adapters;

import com.github.thiagodutra.coopvoteservice.domain.dto.AgendaDTO;
import com.github.thiagodutra.coopvoteservice.domain.dto.VotingSessionDTO;

public interface AgendaAdapter {
    
    AgendaDTO createVotingSession(Long agendaId, VotingSessionDTO votingSession);
    
}
