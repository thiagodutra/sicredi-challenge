package com.github.thiagodutra.coopvoteservice.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.github.thiagodutra.coopvoteservice.domain.dto.VoteDTO;

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

    @Column(unique = true)
    private String cpf;

    @Column(nullable = false)
    private String vote;

    @ManyToOne
    @JoinColumn(name = "voting_session_id", nullable = false)
    private VotingSession votingSession;


    public Vote(String cpf, String vote) {
        this.vote = vote;
        this.cpf = cpf;
    }

    // public List<VoteDTO> voteEntityCollectionToDTO(List<Vote> votes) {
    //     return votes.stream().map(vote -> mapToDTO(vote))
    //     .collect(Collectors.toList());
    // }

    
    public VoteDTO mapToDTO(Vote vote) {
        return new VoteDTO(vote.getId(), vote.getCpf(), vote.getVote());
    }

}
