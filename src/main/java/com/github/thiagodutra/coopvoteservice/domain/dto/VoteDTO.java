package com.github.thiagodutra.coopvoteservice.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class VoteDTO {

    @Getter
    private Long id;
    @Getter
    @NotBlank
    @Size(min=11, max=11)
    private String cpf;
    @Getter
    @NotBlank
    private String vote;
    
}
