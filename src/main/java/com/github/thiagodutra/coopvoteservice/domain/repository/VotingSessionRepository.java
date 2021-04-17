package com.github.thiagodutra.coopvoteservice.domain.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.github.thiagodutra.coopvoteservice.domain.entities.VotingSession;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VotingSessionRepository extends JpaRepository<VotingSession, Long>{


    List<VotingSession> findByTopicName(String topicName);
    List<VotingSession> findByEndingVoteTimeLessThan(LocalDateTime endingVoteTime);
    List<VotingSession> findAllByStartingVoteTimeGreaterThanEqualsAndEndingTimeVoteLessThanEqual(LocalDateTime startingVoteTime);

    // @Query(value = "from tb_voting_session t where :time between :startingVoteTime and :endingVotingTime")
    // List<VotingSession> findOpenVotingSessions
    
}
