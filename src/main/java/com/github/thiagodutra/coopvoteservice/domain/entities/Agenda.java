package com.github.thiagodutra.coopvoteservice.domain.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "voting_session_id")
    private VotingSession votingSession;

    public Agenda (String name) {
        this.name = name;
    }

    public Agenda (String name, VotingSession votingSession) {
        this.name = name;
        this.votingSession = votingSession;
    }

    public AgendaDTO mapToDTO() {
        VotingSessionDTO votingSessionDTO = this.getVotingSession() != null ? this.getVotingSession().mapToDTO() : null;
        return new AgendaDTO(this.getId(), this.getName(), votingSessionDTO);
    }
}
