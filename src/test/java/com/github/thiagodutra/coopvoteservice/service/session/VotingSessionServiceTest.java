package com.github.thiagodutra.coopvoteservice.service.session;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.github.thiagodutra.coopvoteservice.domain.dto.VotingSessionDTO;
import com.github.thiagodutra.coopvoteservice.domain.entities.Agenda;
import com.github.thiagodutra.coopvoteservice.domain.entities.VotingSession;
import com.github.thiagodutra.coopvoteservice.domain.repository.VotingSessionRepository;
import com.github.thiagodutra.coopvoteservice.domain.service.VotingSessionService;
import com.github.thiagodutra.coopvoteservice.domain.service.votingsession.VotingSessionServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VotingSessionServiceTest {

    @Mock VotingSessionRepository mockedVotingSessionRepository;

    @InjectMocks VotingSessionService mockedVotingSessionService = new VotingSessionServiceImpl();


    @Test
    public void shouldCreateAVotingSession(){
        final Agenda agenda = new Agenda(1l, "Agenda 01", null);
        final VotingSession votingSession = new VotingSession("VotingSession01", 10L);
        votingSession.setAlreadyProcessed(Boolean.FALSE);
        votingSession.setAgenda(agenda);

        VotingSession votingSessionWithDefaultVotingTime = new VotingSession("VotingSessionDefault", null);
        
        when(mockedVotingSessionRepository.save(any(VotingSession.class))).thenReturn(votingSession);

        VotingSession savedVotingSession = mockedVotingSessionService.save(new VotingSessionDTO(null, "VotingSession01", 10l));

        assertAll("Should verify Voting Session attributes",
            () -> assertEquals(savedVotingSession.getSessionName(), votingSession.getSessionName()),
            () -> assertEquals(savedVotingSession.getStartingVoteTime(), votingSession.getStartingVoteTime()),
            () -> assertEquals(savedVotingSession.getEndingVoteTime(), votingSession.getStartingVoteTime().plusMinutes(10l)),
            () -> assertEquals(savedVotingSession.getAlreadyProcessed(), votingSession.getAlreadyProcessed()),
            () -> assertEquals(votingSessionWithDefaultVotingTime.getEndingVoteTime(), votingSessionWithDefaultVotingTime.getStartingVoteTime().plusMinutes(1l))
        );
    }

    @Test
    public void shouldFindAllClosedSessionsThatCanBeProcessed(){
        LocalDateTime time = LocalDateTime.now();
        
        when(mockedVotingSessionRepository.findByAlreadyProcessedFalseAndEndingVoteTimeLessThan(time)).thenReturn(createFilterAndReturnList());

        List<VotingSession> votingSessions = mockedVotingSessionService.findClosedSessionsToProcess(time);

        assertAll("Should verify retrieving logic to process voting session results",
            () -> assertEquals(2, votingSessions.size()),
            () -> assertTrue(
                votingSessions.stream()
                    .map(VotingSession::getSessionName)
                        .collect(Collectors.toList())
                            .contains("VotingSession1")),
            () -> assertTrue(
                votingSessions.stream()
                    .map(VotingSession::getSessionName)
                        .collect(Collectors.toList())
                            .contains("VotingSession3"))
        );
    }

    private List<VotingSession> createFilterAndReturnList() {
        VotingSession votingSession1 = new VotingSession("VotingSession1", 10l);
        votingSession1.setAlreadyProcessed(Boolean.FALSE);
        VotingSession votingSession2 = new VotingSession("VotingSession2", 10l);
        votingSession2.setAlreadyProcessed(Boolean.TRUE);
        VotingSession votingSession3 = new VotingSession("VotingSession3", 10l);
        votingSession3.setAlreadyProcessed(Boolean.FALSE);
        VotingSession votingSession4 = new VotingSession("VotingSession4", 10l);
        votingSession4.setAlreadyProcessed(Boolean.TRUE);

        return List.of(votingSession1, votingSession2, votingSession3, votingSession4).stream()
            .filter(vs -> vs.getAlreadyProcessed() == Boolean.FALSE && vs.getEndingVoteTime().isAfter(LocalDateTime.now()))
            .collect(Collectors.toList());
    }
}