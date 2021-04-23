package com.github.thiagodutra.coopvoteservice.domain.service.agenda;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import com.github.thiagodutra.coopvoteservice.domain.dto.AgendaDTO;
import com.github.thiagodutra.coopvoteservice.domain.dto.VotingSessionDTO;
import com.github.thiagodutra.coopvoteservice.domain.entities.Agenda;
import com.github.thiagodutra.coopvoteservice.domain.entities.VotingSession;
import com.github.thiagodutra.coopvoteservice.domain.messages.ApplicationMessages;
import com.github.thiagodutra.coopvoteservice.domain.repository.AgendaRepository;
import com.github.thiagodutra.coopvoteservice.domain.service.AgendaService;
import com.github.thiagodutra.coopvoteservice.domain.service.VotingSessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AgendaServiceImpl implements AgendaService {

    @Autowired
    AgendaRepository agendaRepository;

    @Autowired
    VotingSessionService votingSessionService;

    @Override
    public List<AgendaDTO> getAllAgenda() {
        log.info("Retrieving all agendas...");
        return agendaRepository.findAll().stream()
            .map(Agenda::mapToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public AgendaDTO getAgendaById(Long id) {
        log.info("Retrieving agenda...");
        log.debug(String.format("Trying to retrieve agenda with id:%s", id.toString()));
        return agendaRepository.findById(id)
        .map(Agenda::mapToDTO)
        .orElseThrow(() -> new NoSuchElementException(ApplicationMessages.AGENDA_DOES_NOT_EXISTS));
    }

    @Override
    public AgendaDTO createAgenda(AgendaDTO newAgenda) {
        log.info("Creating agenda...");
        log.debug(String.format("Creating agenda. name:%s ", newAgenda.getName()));
        Agenda agenda = newAgenda.mapToEntity();
        return agendaRepository.save(agenda).mapToDTO();
    }

    @Override
    public AgendaDTO createVotingSession(Long agendaId, VotingSessionDTO votingSession) {
        log.info("Creating agenda's voting session");
        log.debug(String.format("Creating voting session: %n Retrieving corresponding Agenda"));
        Optional<Agenda> agendaOptional = agendaRepository.findById(agendaId);
        if (agendaOptional.isPresent()) {
            Agenda agenda = agendaOptional.get();
            log.debug(String.format("Found agenda. ID:%s, Name:%s", agenda.getId().toString(), agenda.getName()));
            VotingSession votingSessionFromDto = votingSession.mapToEntity();
            votingSessionFromDto.setAlreadyProcessed(Boolean.FALSE);
            agenda.setVotingSession(votingSessionFromDto);
            log.debug("Created relation between Agenda and Voting Session.");
            return agendaRepository.save(agenda).mapToDTO();
        }
        log.error(String.format("Agenda with the given id: %s was not found.", agendaId.toString()));
        throw new NoSuchElementException(ApplicationMessages.AGENDA_DOES_NOT_EXISTS);
    }
}