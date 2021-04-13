package com.github.thiagodutra.coopvoteservice.domain.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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

    @NonNull
    @Column(name = "name", length = 50)
    private String name;

    @NonNull
    @Column(name = "created_in")
    private LocalDateTime createdIn;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<VotingSession> votingSession = new HashSet<VotingSession>();

    public Agenda (Long id, String name) throws Exception {
        validateName(name);
        this.id = id;
        this.createdIn = LocalDateTime.now();
        this.name = name;
    }

    //TODO DEFINE AND THROW A CUSTOM EXCPETION INSTEAD OF A GENERIC ONE
    private void validateName(String name) throws Exception {
        if (name.isEmpty() || name.isBlank()) {
            throw new Exception(String.format("Attribute %s is mandatory", "name"));
        }
    }

}
