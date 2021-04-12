package com.github.thiagodutra.domain.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "Agenda")
public class Agenda {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @NonNull
    @Column(name = "name", length = 50)
    private String name;

    @Getter
    @NonNull
    @Column(name = "created_in")
    private LocalDateTime date;

    // private Set<VotingSession> votingSession = new HashSet<VotingSession>;


    public Agenda (Long id, String name) throws Exception {
        validateName(name);
        this.id = id;
        this.date = LocalDateTime.now();
        this.name = name;
    }

    //TODO DEFINE AND THROW A CUSTOM EXCPETION INSTEAD OF A GENERIC ONE
    private void validateName(String name) throws Exception {
        if (name.isEmpty() || name.isBlank()) {
            throw new Exception(String.format("Attribute %s is mandatory", "name"));
        }
    }

}
