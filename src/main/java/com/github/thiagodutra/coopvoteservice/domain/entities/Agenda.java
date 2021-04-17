package com.github.thiagodutra.coopvoteservice.domain.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.github.thiagodutra.coopvoteservice.domain.dto.AgendaDTO;
import com.github.thiagodutra.coopvoteservice.domain.dto.VotingSessionDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "tb_agenda")
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NonNull
    @Column(name = "name", length = 50)
    private String name;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "agenda")
    private List<VotingSession> votingSession = new ArrayList<>();

    public Agenda (String name) {
        this.name = name;
    }

    public Agenda (String name, List<VotingSession> votingSessions) {
        this.name = name;
        this.votingSession = votingSessions;
    }

    public AgendaDTO mapToDTO() {
        return new AgendaDTO(this.getId(), this.getName(), votingSessionToDTO(this.getVotingSession()));
    }

    private List<VotingSessionDTO> votingSessionToDTO (List<VotingSession> votingSessions) {
        return votingSessions.stream().map(VotingSession::mapToDTO).collect(Collectors.toList());
    }

}
