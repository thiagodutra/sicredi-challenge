package com.github.thiagodutra.domain.repository;

import com.github.thiagodutra.domain.entities.Agenda;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long>{
    
}
