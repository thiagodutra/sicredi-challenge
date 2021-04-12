package com.github.thiagodutra.domain.dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class AgendaDTO {
    private Long id;
    @NonNull
    private Long name;
    // private Set<VotingSession> votingSession;
}
