package com.github.thiagodutra.coopvoteservice.domain.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.github.thiagodutra.coopvoteservice.domain.entities.VotingSession;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VotingSessionRepository extends JpaRepository<VotingSession, Long>{

    List<VotingSession> findBySessionName (String sessionName);
    List<VotingSession> findByAlreadyProcessedFalseAndEndingVoteTimeLessThan(LocalDateTime actualTime);    
}
