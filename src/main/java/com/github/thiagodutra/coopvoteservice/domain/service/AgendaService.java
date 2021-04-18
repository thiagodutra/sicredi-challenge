package com.github.thiagodutra.coopvoteservice.domain.service;

import java.util.List;

import com.github.thiagodutra.coopvoteservice.domain.dto.AgendaDTO;
import com.github.thiagodutra.coopvoteservice.domain.dto.VotingSessionDTO;

public interface AgendaService {

    List<AgendaDTO> getAllAgenda();
    AgendaDTO getAgendaById(Long id);
    Long createAgenda(AgendaDTO newAgenda);
    AgendaDTO createVotingSession(Long agendaId, VotingSessionDTO votingSession);
}
