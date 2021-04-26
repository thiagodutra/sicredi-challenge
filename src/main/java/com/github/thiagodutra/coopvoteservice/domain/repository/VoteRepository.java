package com.github.thiagodutra.coopvoteservice.domain.repository;

import java.util.List;

import com.github.thiagodutra.coopvoteservice.domain.entities.Vote;
import com.github.thiagodutra.coopvoteservice.domain.entities.VotingSession;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long>{

    List<Vote> findAllByVotingSession(VotingSession votingSession);
    
}