package com.github.thiagodutra.coopvoteservice.domain.dto;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class VotingSessionDTO {
    
    @Getter
    private Long id;
    @Getter
    @NonNull
    @NotBlank
    @Size(max=50)
    private String topicName;
    @Getter
    private Integer votingDurationInMinutes;
    @Getter
    private Set<VoteDTO> votes;
    
}
