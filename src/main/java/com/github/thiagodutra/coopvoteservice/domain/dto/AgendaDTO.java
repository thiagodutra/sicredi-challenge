package com.github.thiagodutra.coopvoteservice.domain.dto;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

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
    @Min(3)
    @Max(50)
    private String name;
    
    private VotingSessionDTO votingSession;

    public Agenda mapToEntity(){
        return new Agenda(this.getName());
    }
}
