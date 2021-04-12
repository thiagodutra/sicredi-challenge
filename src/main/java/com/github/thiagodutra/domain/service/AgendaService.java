package com.github.thiagodutra.domain.service;

import com.github.thiagodutra.domain.dto.AgendaDTO;

public interface AgendaService {

    void getAgendaById(Long id);
    void createAgenda(AgendaDTO newAgenda);
    void getAgendaVotingSession(Long id);
}
