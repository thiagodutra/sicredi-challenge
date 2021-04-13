package com.github.thiagodutra.coopvoteservice.domain.dto;


import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.github.thiagodutra.coopvoteservice.domain.entities.Agenda;
import com.github.thiagodutra.coopvoteservice.domain.entities.VotingSession;

import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AgendaDTO {

    private Long id;
    @NonNull
    @NotBlank
    private String name;
    private LocalDateTime createdIn;
    private Set<VotingSession> votingSession;

    public Agenda mapToEntity() throws Exception {
        return new Agenda(this.id, this.name);
    }
}
