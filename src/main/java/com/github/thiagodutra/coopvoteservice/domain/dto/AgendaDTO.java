package com.github.thiagodutra.coopvoteservice.domain.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.github.thiagodutra.coopvoteservice.domain.entities.Agenda;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AgendaDTO {


    private Long id;
    @NotBlank
    @Size(max = 50)
    private String name;
    
    private VotingSessionDTO votingSession;

    public Agenda mapToEntity(){
        return new Agenda(this.getName());
    }
}
