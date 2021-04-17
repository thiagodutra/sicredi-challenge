package com.github.thiagodutra.coopvoteservice.domain.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.github.thiagodutra.coopvoteservice.domain.entities.VotingSession;

import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class VotingSessionDTO {
    
    private Long id;
    @NonNull
    @NotBlank
    @Size(max=50)
    private String topicName;
    private Long votingDurationInMinutes;
    private List<VoteDTO> votes;

    
    public VotingSession mapToEntity() {
        return new VotingSession(this.getTopicName(), this.getVotingDurationInMinutes());
    }

    
}
