package com.github.thiagodutra.coopvoteservice.api.controllers;

import java.util.List;

import com.github.thiagodutra.coopvoteservice.domain.dto.AgendaDTO;
import com.github.thiagodutra.coopvoteservice.domain.service.AgendaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cooperativa/agenda")
public class AgendaController {
    
    @Autowired
    AgendaService agendaService;

    @GetMapping
    public ResponseEntity<List<AgendaDTO>> getAllAgenda() {
        try {
            return ResponseEntity.ok(agendaService.getAllAgenda());
        } catch (Exception e ){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendaDTO> getAgendaById(@PathVariable Long id) {
        AgendaDTO agenda = new AgendaDTO();
        try {
            agenda = agendaService.getAgendaById(id);
            return ResponseEntity.ok(agenda);
        } catch (Exception e ){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<Long> createAgenda(@RequestBody final AgendaDTO agenda){
        try {
            return ResponseEntity.ok(agendaService.createAgenda(agenda));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
