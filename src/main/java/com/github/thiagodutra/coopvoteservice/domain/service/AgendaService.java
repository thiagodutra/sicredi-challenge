package com.github.thiagodutra.coopvoteservice.domain.service;

import java.util.List;

import com.github.thiagodutra.coopvoteservice.domain.dto.AgendaDTO;

public interface AgendaService {

    List<AgendaDTO> getAllAgenda();
    AgendaDTO getAgendaById(Long id) throws Exception;
    Long createAgenda(AgendaDTO newAgenda) throws Exception;
    void getAgendaVotingSessions(Long id);
}
