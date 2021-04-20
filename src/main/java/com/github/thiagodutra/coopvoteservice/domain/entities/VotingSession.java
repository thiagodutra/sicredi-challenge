package com.github.thiagodutra.coopvoteservice.domain.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.github.thiagodutra.coopvoteservice.domain.dto.VotingSessionDTO;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity(name = "tb_voting_session")
public class VotingSession {

    private static final Long DEFAULT_VOTING_DURATION = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voting_session_id")
    private Long id;
    @Column(name = "voting_session_name")
    private String sessionName;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDateTime startingVoteTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDateTime endingVoteTime;
    
    private Long votingDurationInMinutes;

    @OneToOne(mappedBy = "votingSession")
    private Agenda agenda;

    public VotingSession(String sessionName, Long votingDurationInMinutes){
        this.sessionName = sessionName;
        this.startingVoteTime = LocalDateTime.now();
        this.votingDurationInMinutes = votingDurationInMinutes == null ? DEFAULT_VOTING_DURATION : votingDurationInMinutes;
        this.endingVoteTime = calculateEndingVotingTime(votingDurationInMinutes);
    }

    private LocalDateTime calculateEndingVotingTime(Long votingDurationInMinutes) {
        return LocalDateTime.now().plusMinutes(votingDurationInMinutes);
    }

    public VotingSessionDTO mapToDTO(){
        return new VotingSessionDTO(this.getId(), this.getSessionName(), this.getVotingDurationInMinutes());
    }
}
