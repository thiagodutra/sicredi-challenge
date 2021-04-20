package com.github.thiagodutra.coopvoteservice.domain.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.github.thiagodutra.coopvoteservice.domain.dto.VoteDTO;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "tb_vote")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "document", unique = true)
    private String cpf;

    @Column(nullable = false)
    private String vote;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @Column(nullable = false)
    private LocalDateTime timeOfVote;

    @OneToOne
    @JoinColumn(name = "voting_session_id")
    private VotingSession votingSession;

    
    public Vote(String cpf, String vote) {
        this.vote = vote;
        this.cpf = cpf;
        this.timeOfVote = LocalDateTime.now();
    }

    public Vote(String cpf, String vote, VotingSession votingSession) {
        this.vote = vote;
        this.cpf = cpf;
        this.votingSession = votingSession;
        this.timeOfVote = LocalDateTime.now();
    }
    
    public VoteDTO mapToDTO() {
        return new VoteDTO(this.getId(), this.getCpf());
    }
}