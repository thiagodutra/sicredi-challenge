package com.github.thiagodutra.coopvoteservice.service.agenda;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
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
        AgendaDTO agendaDTO = new AgendaDTO(agendaEntity.getId(), agendaEntity.getName(),null);

        when(mockedAgendaRepository.save(any())).thenReturn(agendaEntity);

        Agenda agenda = mockedAgendaService.createAgenda(agendaDTO);

        assertAll("Should create an Agenda", 
            () -> assertEquals(agendaName, agenda.getName()),
            () -> assertEquals(randomId, agenda.getId()),
            () -> assertNull(agenda.getVotingSession())
        );

        verify(mockedAgendaRepository).save(any());
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

        Agenda agenda = mockedAgendaService.createVotingSession(randomId, votingSession);

        assertAll("Should create and add a VotingSession to an existing Agenda",
            () -> assertEquals(agendaName, agenda.getName()),
            () -> assertEquals(randomId, agenda.getId()),
            () -> assertNotNull(agenda.getVotingSession()),
            () -> assertEquals(sessionName, agenda.getVotingSession().getSessionName()),
            () -> assertNotNull(agenda.getVotingSession().getVotingDurationInMinutes())
            );

        assertAll ("Should verify VotingSessions attributes",
            () -> assertTrue(agendaEntity.getVotingSession().getStartingVoteTime().plusMinutes(15L)
                .isEqual(agendaEntity.getVotingSession().getEndingVoteTime())),
            () -> assertTrue(agendaEntity.getVotingSession().getEndingVoteTime()
                .isAfter(agendaEntity.getVotingSession().getStartingVoteTime().plusMinutes(14L))));

        verify(mockedAgendaRepository).findById(randomId);
        verify(mockedAgendaRepository).save(agendaEntity);
    }
    
    @Test
    public void shouldThrowNoSuchElementExceptionWhenEnityIsNotFound(){
        Long randomId = new Random().nextLong();
        String sessionName = RandomString.make(25);
        VotingSessionDTO votingSession = new VotingSessionDTO(randomId, sessionName, 15L);
        when(mockedAgendaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,  () -> mockedAgendaService.createVotingSession(10l, votingSession), ApplicationMessages.AGENDA_DOES_NOT_EXISTS);
        
        verify(mockedAgendaRepository).findById(10l);
    }
}