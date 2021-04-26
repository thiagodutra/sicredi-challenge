package com.github.thiagodutra.coopvoteservice.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import com.github.thiagodutra.coopvoteservice.domain.dto.AgendaDTO;
import com.github.thiagodutra.coopvoteservice.domain.dto.VotingSessionDTO;
import com.github.thiagodutra.coopvoteservice.domain.response.DefaultErrorResponse;
import com.github.thiagodutra.coopvoteservice.domain.service.AgendaService;
import com.github.thiagodutra.coopvoteservice.domain.service.VotingSessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1")
public class AgendaController {

    @Autowired
    AgendaService agendaService;
    @Autowired
    VotingSessionService votingSessionService;

    @ExceptionHandler(NoSuchElementException.class)
    ResponseEntity<DefaultErrorResponse> handleException(NoSuchElementException exception) {
        return new ResponseEntity<>(
                new DefaultErrorResponse("Agenda", "Cannot find the element with the given id", exception.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<DefaultErrorResponse> handleException(MethodArgumentNotValidException exception) {
        return new ResponseEntity<>(
                new DefaultErrorResponse("Agenda", "Object failed name validation, try to put a name under 50 characters", exception.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Method to retrieve All Agendas in Database", 
        notes = "Use to retrieve all agendas",
        response = AgendaDTO.class)
    @GetMapping("/agenda")
    public ResponseEntity<List<AgendaDTO>> getAllAgenda() {
        return ResponseEntity.ok(agendaService.getAllAgenda());
    }
    @ApiOperation(value = "Method to retrieve one Agenda by its ID", 
        notes = "Provide an id to look up for an especific agenda",
        response = AgendaDTO.class)
    @GetMapping("/agenda/{id}")
    public ResponseEntity<AgendaDTO> getAgendaById(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(agendaService.getAgendaById(id).mapToDTO());
    }
    @ApiOperation(value = "Method to create an Agenda", 
        notes = "To create a new Agenda",
        response = AgendaDTO.class)
    @PostMapping("/agenda")
    public ResponseEntity<AgendaDTO> createAgenda(@Valid @RequestBody final AgendaDTO agenda) {
        return ResponseEntity.ok(agendaService.createAgenda(agenda).mapToDTO());
    }
    @ApiOperation(value = "Method to create a Voting Session related to one Agenda", 
        notes = "Creates a voting session relationed to the given agenda id",
        response = AgendaDTO.class)
    @PostMapping("/agenda/{agendaId}/create-session")
    public ResponseEntity<AgendaDTO> createSession(@PathVariable @Min(1) Long agendaId,
    @Valid @RequestBody VotingSessionDTO votingSession) {
        return ResponseEntity.ok(agendaService.createVotingSession(agendaId, votingSession).mapToDTO());
    }
}