package com.github.thiagodutra.coopvoteservice.domain.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "tb_voting_session")
public class VotingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String topicName;
    private LocalDateTime startingVoteTime = LocalDateTime.now();
    private LocalDateTime endingVoteTime;
    private Integer votingDurationInMinutes;
    @OneToMany(fetch = FetchType.EAGER)
    private final Set<Vote> votes = new HashSet<>();

    // public VotingSessionDTO mapToDTO(){
    //     return new VotingSessionDTO(this.getId(), this.getTopicName(), votesToDTO());
    // }

    // private Set<VoteDTO> votesToDTO() {
    //     return this.getVotes().stream().map(vote -> new VoteDTO(vote.getId(), vote.getCpf(), vote.getVote()))
    //             .collect(Collectors.toSet());
    // }

}
