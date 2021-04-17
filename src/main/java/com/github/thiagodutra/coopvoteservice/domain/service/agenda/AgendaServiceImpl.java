package com.github.thiagodutra.coopvoteservice.domain.service.agenda;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.github.thiagodutra.coopvoteservice.domain.dto.AgendaDTO;
import com.github.thiagodutra.coopvoteservice.domain.entities.Agenda;
import com.github.thiagodutra.coopvoteservice.domain.repository.AgendaRepository;
import com.github.thiagodutra.coopvoteservice.domain.service.AgendaService;
import com.github.thiagodutra.coopvoteservice.domain.service.VotingSessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AgendaServiceImpl implements AgendaService {

    @Autowired
    AgendaRepository agendaRepository;

    @Autowired
    VotingSessionService votingSessionService;

    @Override
    public List<AgendaDTO> getAllAgenda() {
        return agendaRepository.findAll().stream().map(Agenda::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public AgendaDTO getAgendaById(Long id) throws Exception {
        return agendaRepository.findById(id)
        .map(Agenda::mapToDTO)
        .orElseThrow(() -> new Exception(""));
    }

    @Override
    public Long createAgenda(AgendaDTO newAgenda) {
        Optional<Agenda> agenda = Optional.ofNullable(newAgenda)
        .map(AgendaDTO::mapToEntity);
        return agendaRepository.save(agenda.get()).getId();
    }

    @Override
    public void getAgendaVotingSessions(Long id) {
        String a = "b"; 
    }

}
