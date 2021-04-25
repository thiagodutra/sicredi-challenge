package com.github.thiagodutra.coopvoteservice.service.vote;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Random;

import com.github.thiagodutra.coopvoteservice.domain.dto.VoteDTO;
import com.github.thiagodutra.coopvoteservice.domain.entities.Vote;
import com.github.thiagodutra.coopvoteservice.domain.entities.VotingSession;
import com.github.thiagodutra.coopvoteservice.domain.exceptions.ClosedVotingSessionException;
import com.github.thiagodutra.coopvoteservice.domain.exceptions.InvalidDocumentNumberException;
import com.github.thiagodutra.coopvoteservice.domain.messages.ApplicationMessages;
import com.github.thiagodutra.coopvoteservice.domain.repository.VoteRepository;
import com.github.thiagodutra.coopvoteservice.domain.service.DocumentService;
import com.github.thiagodutra.coopvoteservice.domain.service.VoteService;
import com.github.thiagodutra.coopvoteservice.domain.service.VotingSessionService;
import com.github.thiagodutra.coopvoteservice.domain.service.vote.VoteServiceImpl;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VoteServiceTest {

    @Mock
    VoteRepository mockedVoteRepository;
    @Mock
    VotingSessionService mockedVotingSessionService;
    @Mock
    DocumentService mockedDocumentService;
    @InjectMocks
    VoteService mockedVoteService = new VoteServiceImpl();


    @Test
    public void shouldComputeAVote() {
        Vote vote = new Vote("123456789", "yes");
        VotingSession votingSession = new VotingSession("VotingSession01", 10l);
        VoteDTO voteDTO = vote.mapToDTO();

        when(mockedVoteRepository.save(any())).thenReturn(vote);
        when(mockedVotingSessionService.findById(1l)).thenReturn(votingSession);
        when(mockedDocumentService.validateDocument(vote.getCpf())).thenReturn(Boolean.TRUE);

        Vote savedVote = mockedVoteService.processVote(1l, voteDTO);

        assertAll("Should compute a vote",
            () -> assertEquals(savedVote.getCpf(), vote.getCpf()),
            () -> assertEquals(savedVote.getVote(), vote.getVote()),
            () -> assertTrue(savedVote.getTimeOfVote().isBefore(votingSession.getEndingVoteTime()))
        );

        verify(mockedDocumentService).validateDocument(vote.getCpf());
        verify(mockedVotingSessionService).findById(1l);
    }

    @Test
    public void shoudThrowNoSuchelementException(){
                      
        when(mockedVotingSessionService.findById(anyLong())).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class,  () -> mockedVotingSessionService.findById(10l), ApplicationMessages.VOTING_SESSION_DOES_NOT_EXISTS);
    }

    @Test
    public void shouldThrowClosedVotingSessionException() {
        Long randomId = new Random().nextLong();
        String sessionName = RandomString.make(25);
        VotingSession votingSession = new VotingSession(sessionName, 10l);
        votingSession.setEndingVoteTime(votingSession.getStartingVoteTime().minusMinutes(10l));
        VoteDTO voteDto = new VoteDTO(null, "123456", "yes");

        when(mockedVotingSessionService.findById(randomId)).thenReturn(votingSession);

        assertThrows(ClosedVotingSessionException.class, () -> mockedVoteService.processVote(randomId, voteDto), ApplicationMessages.VOTING_SESSION_IS_CLOSED);

        verify(mockedVotingSessionService).findById(randomId);
    }

    @Test
    public void shouldVoteNotSupportedExceptionForInvalidDocument() {
        String cpf = "123456789";
        Long randomId = new Random().nextLong();
        String sessionName = RandomString.make(25);
        VotingSession votingSession = new VotingSession(sessionName, 10l);
        VoteDTO voteDto = new VoteDTO(null, cpf, "yes");

        when(mockedVotingSessionService.findById(randomId)).thenReturn(votingSession);
        when(mockedDocumentService.validateDocument(cpf)).thenThrow(InvalidDocumentNumberException.class);
        
        assertThrows(InvalidDocumentNumberException.class, () -> mockedVoteService.processVote(randomId, voteDto), ApplicationMessages.SOMETHING_WEIRD_OCCURRED);

        verify(mockedVotingSessionService).findById(randomId);
        verify(mockedDocumentService).validateDocument(cpf);

    }
}