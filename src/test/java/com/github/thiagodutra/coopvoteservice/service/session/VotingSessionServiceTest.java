package com.github.thiagodutra.coopvoteservice.service.session;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
    public void test(){
        assertTrue(true);
    }
    
    
}
