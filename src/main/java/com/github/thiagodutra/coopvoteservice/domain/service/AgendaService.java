package com.github.thiagodutra.coopvoteservice.domain.service;

import java.util.List;

import com.github.thiagodutra.coopvoteservice.domain.dto.AgendaDTO;
import com.github.thiagodutra.coopvoteservice.domain.dto.VotingSessionDTO;
import com.github.thiagodutra.coopvoteservice.domain.entities.Agenda;

public interface AgendaService {

    List<AgendaDTO> getAllAgenda();
    Agenda getAgendaById(Long id);
    Agenda createAgenda(AgendaDTO newAgenda);
    Agenda createVotingSession(Long agendaId, VotingSessionDTO votingSession);
}
