package com.github.thiagodutra.coopvoteservice.domain.service.votingsession;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import com.github.thiagodutra.coopvoteservice.domain.dto.VotingSessionDTO;
import com.github.thiagodutra.coopvoteservice.domain.entities.VotingSession;
import com.github.thiagodutra.coopvoteservice.domain.messages.ApplicationMessages;
import com.github.thiagodutra.coopvoteservice.domain.repository.VotingSessionRepository;
import com.github.thiagodutra.coopvoteservice.domain.service.VotingSessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VotingSessionServiceImpl implements VotingSessionService{

    @Autowired
    VotingSessionRepository votingSessionRepository;

    @Override
    public VotingSession save(VotingSessionDTO votingSessionDTO) {
        log.info("Saving voting session.");
        VotingSession votingSession = votingSessionDTO.mapToEntity();
        log.debug("Saving voting session: " + votingSession.toString());
        return votingSessionRepository.save(votingSession); 
    }

    @Override
    public VotingSession findById(Long id){
        log.debug(String.format("Trying to retrieve VotingSession with ID:%s", id.toString()));
        return votingSessionRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException(ApplicationMessages.VOTING_SESSION_DOES_NOT_EXISTS));     
    }

    @Override
    public List<VotingSession> findClosedSessionsToProcess(final LocalDateTime actualTime) {
        return votingSessionRepository.findByAlreadyProcessedFalseAndEndingVoteTimeLessThan(actualTime);
    }
}