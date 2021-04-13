package com.github.thiagodutra.coopvoteservice.domain.dto;


import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.github.thiagodutra.coopvoteservice.domain.entities.Agenda;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class AgendaDTO {

    @Getter
    private Long id;
    @Getter
    @NotBlank
    @Size(max = 50)
    private String name;
    @Getter
    private Set<VotingSessionDTO> votingSession;

    public Agenda mapToEntity() {
        return new Agenda(this.id, this.name);
    }
}
