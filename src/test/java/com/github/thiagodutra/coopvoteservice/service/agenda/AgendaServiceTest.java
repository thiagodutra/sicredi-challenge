package com.github.thiagodutra.coopvoteservice.service.agenda;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

import com.github.thiagodutra.coopvoteservice.domain.dto.AgendaDTO;
import com.github.thiagodutra.coopvoteservice.domain.dto.VotingSessionDTO;
import com.github.thiagodutra.coopvoteservice.domain.entities.Agenda;
import com.github.thiagodutra.coopvoteservice.domain.messages.ApplicationMessages;
import com.github.thiagodutra.coopvoteservice.domain.repository.AgendaRepository;
import com.github.thiagodutra.coopvoteservice.domain.service.AgendaService;
import com.github.thiagodutra.coopvoteservice.domain.service.agenda.AgendaServiceImpl;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AgendaServiceTest {

    @Mock
    private AgendaRepository mockedAgendaRepository;
    @InjectMocks
    private AgendaService mockedAgendaService = new AgendaServiceImpl();

    @Test
    public void shouldCreateAnAgenda(){
        Long randomId = new Random().nextLong();
        String agendaName = "Created Agenda";
        Agenda agendaEntity = new Agenda(randomId, agendaName, null);
        
        when(mockedAgendaRepository.save(any(Agenda.class))).thenReturn(agendaEntity);

        AgendaDTO agendaDTO = mockedAgendaService.createAgenda(agendaEntity.mapToDTO());

        assertAll("Should create an Agenda", 
            () -> assertEquals(agendaName, agendaDTO.getName()),
            () -> assertEquals(randomId, agendaDTO.getId()),
            () -> assertNull(agendaDTO.getVotingSession())
        );
    }

    @Test
    public void shouldCreateAVotingSessionIntoAnAgenda(){
        Long randomId = new Random().nextLong();
        String agendaName = RandomString.make(25);
        String sessionName = RandomString.make(25);
        VotingSessionDTO votingSession = new VotingSessionDTO(randomId, sessionName, 15L);
        Agenda agendaEntity = new Agenda(randomId, agendaName, votingSession.mapToEntity());

        when(mockedAgendaRepository.findById(randomId)).thenReturn(Optional.of(agendaEntity));
        when(mockedAgendaRepository.save(agendaEntity)).thenReturn(agendaEntity);

        AgendaDTO agendaDTO = mockedAgendaService.createVotingSession(randomId, votingSession);

        assertAll("Should create and add a VotingSession to an existing Agenda",
            () -> assertEquals(agendaName, agendaDTO.getName()),
            () -> assertEquals(randomId, agendaDTO.getId()),
            () -> assertNotNull(agendaDTO.getVotingSession()),
            () -> assertEquals(sessionName, agendaDTO.getVotingSession().getTopicName()),
            () -> assertNotNull(agendaDTO.getVotingSession().getVotingDurationInMinutes())
            );

        assertAll ("Should verify VotingSessions attributes",
            () -> assertTrue(agendaEntity.getVotingSession().getStartingVoteTime().plusMinutes(15L)
                .isEqual(agendaEntity.getVotingSession().getEndingVoteTime())),
            () -> assertTrue(agendaEntity.getVotingSession().getEndingVoteTime()
                .isAfter(agendaEntity.getVotingSession().getStartingVoteTime().plusMinutes(14L))));
    }
    
    @Test
    public void shouldThrowNoSuchElementExceptionWhenEnityIsNotFound(){
        Long randomId = new Random().nextLong();
        String sessionName = RandomString.make(25);
        VotingSessionDTO votingSession = new VotingSessionDTO(randomId, sessionName, 15L);
        when(mockedAgendaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,  () -> mockedAgendaService.createVotingSession(10l, votingSession), ApplicationMessages.AGENDA_DOES_NOT_EXISTS);       
    }
}