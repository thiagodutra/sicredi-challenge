package com.github.thiagodutra.coopvoteservice.api.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import com.github.thiagodutra.coopvoteservice.domain.dto.AgendaDTO;
import com.github.thiagodutra.coopvoteservice.domain.response.DefaultErrorResponse;
import com.github.thiagodutra.coopvoteservice.domain.service.AgendaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/cooperativa/agenda")
public class AgendaController {
    
    @Autowired
    AgendaService agendaService;

    @ExceptionHandler(Exception.class)
    ResponseEntity<DefaultErrorResponse> handleException(Exception exception) {
        //TODO Refactor this and transform this in a method
        if (exception instanceof NoSuchElementException) {
            return new ResponseEntity<>(
                new DefaultErrorResponse(
                    "getAgendaByID",
                    "Element not found with the given id", 
                    "more details"), 
                HttpStatus.NOT_FOUND);
        }
        return null;
    }

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
