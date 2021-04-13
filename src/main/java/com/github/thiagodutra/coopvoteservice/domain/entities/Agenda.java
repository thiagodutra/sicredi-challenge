package com.github.thiagodutra.coopvoteservice.domain.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

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

    @OneToMany(fetch = FetchType.EAGER)
    private Set<VotingSession> votingSession = new HashSet<>();

    public Agenda (String name) {
        this.name = name;
    }

    public Agenda (Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
