package com.github.thiagodutra.coopvoteservice.domain.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.github.thiagodutra.coopvoteservice.domain.dto.VoteDTO;
import com.github.thiagodutra.coopvoteservice.domain.dto.VotingSessionDTO;
import com.github.thiagodutra.coopvoteservice.domain.utils.mappers.VoteMapper;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity(name = "tb_voting_session")
public class VotingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String topicName;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDateTime startingVoteTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDateTime endingVoteTime;
    private Long votingDurationInMinutes;
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "votingSession")
    private final List<Vote> votes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "agenda_id", nullable = false)
    private Agenda agenda;

    public VotingSession(String topicName, Long votingDurationInMinutes){
        this.topicName = topicName;
        this.startingVoteTime = LocalDateTime.now();
        this.votingDurationInMinutes = votingDurationInMinutes;
        this.endingVoteTime = calculateEndingVotingTime(votingDurationInMinutes);
    }

    private LocalDateTime calculateEndingVotingTime(Long votingDurationInMinutes) {
        return getStartingVoteTime() == null ? LocalDateTime.now().plusMinutes(votingDurationInMinutes) : getStartingVoteTime().plusMinutes(votingDurationInMinutes);
    }

    // public List<VotingSessionDTO> mapEntityCollectionToDTO(List<VotingSession> votes) {
    //     return votes.stream().map(votingSession -> mapToDTO(votingSession))
    //     .collect(Collectors.toList());
    // }

    public VotingSessionDTO mapToDTO(){
        List<VoteDTO> votes = VoteMapper.voteEntityCollectionToDTO(this.getVotes());
        return new VotingSessionDTO(this.getId(), this.getTopicName(), this.getVotingDurationInMinutes(), votes);
    }
}
