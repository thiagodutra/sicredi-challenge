package com.github.thiagodutra.coopvoteservice.domain.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.github.thiagodutra.coopvoteservice.domain.entities.VotingSession;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VotingSessionRepository extends JpaRepository<VotingSession, Long>{


    List<VotingSession> findBySessionName (String sessionName);
    List<VotingSession> findByEndingVoteTimeLessThan(LocalDateTime endingVoteTime);
    @Query("Select vs FROM tb_voting_session vs WHERE vs.endingVoteTime < now()")
    List<VotingSession> findAllOpenSessions();
    @Query("from tb_voting_session t where :time between :startingVoteTime and :endingVotingTime")
    List<VotingSession> findOpenVotingSessions(LocalDateTime time);
    
}
