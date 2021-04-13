package com.github.thiagodutra.coopvoteservice.domain.service.agenda;

import java.util.List;
import java.util.stream.Collectors;

import com.github.thiagodutra.coopvoteservice.domain.dto.AgendaDTO;
import com.github.thiagodutra.coopvoteservice.domain.entities.Agenda;
import com.github.thiagodutra.coopvoteservice.domain.repository.AgendaRepository;
import com.github.thiagodutra.coopvoteservice.domain.service.AgendaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AgendaServiceImpl implements AgendaService {

    @Autowired
    AgendaRepository agendaRepository;

    @Override
    public List<AgendaDTO> getAllAgenda() {
        return agendaRepository.findAll().stream()
            .map(agenda -> 
                new AgendaDTO(agenda.getId(), agenda.getName(), agenda.getCreatedIn(), agenda.getVotingSession()))
            .collect(Collectors.toList());
    }

    @Override
    public AgendaDTO getAgendaById(Long id) throws Exception {
        return agendaRepository.findById(id)
        .map(agenda -> 
            new AgendaDTO(agenda.getId(), agenda.getName(), agenda.getCreatedIn(), null))
        .orElseThrow(() -> new Exception(""));
    }

    @Override
    public Long createAgenda(AgendaDTO newAgenda) throws Exception {
        final Agenda agenda = newAgenda.mapToEntity();
        return agendaRepository.save(agenda).getId();
    }

    @Override
    public void getAgendaVotingSession(Long id) {
        String a = "b"; 
    }
}
