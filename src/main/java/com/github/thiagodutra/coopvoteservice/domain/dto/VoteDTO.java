package com.github.thiagodutra.coopvoteservice.domain.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.github.thiagodutra.coopvoteservice.domain.entities.Vote;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class VoteDTO{

    private Long id;
    @NotBlank
    @Size(min=11, max=11)
    private String cpf;
    @NotBlank
    private String vote;

    public Vote mapToEntity(){
        return new Vote(this.getCpf(), this.getVote());
    }
    
}
