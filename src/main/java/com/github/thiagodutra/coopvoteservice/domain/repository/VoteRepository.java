package com.github.thiagodutra.coopvoteservice.domain.repository;

import com.github.thiagodutra.coopvoteservice.domain.entities.Vote;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long>{
    
}