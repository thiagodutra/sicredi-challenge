package com.github.thiagodutra.coopvoteservice.domain.utils.mappers;

import java.util.List;

import com.github.thiagodutra.coopvoteservice.domain.dto.AgendaDTO;
import com.github.thiagodutra.coopvoteservice.domain.dto.VotingSessionDTO;
import com.github.thiagodutra.coopvoteservice.domain.entities.Agenda;
import com.github.thiagodutra.coopvoteservice.domain.entities.VotingSession;

public class AgendaMapper {

    private AgendaMapper(){}

    public static Agenda mapToEntity(AgendaDTO agenda){
        List<VotingSession> votingSession = VotingSessionMapper.mapDtoCollectionToEntity(agenda.getVotingSession());
        return new Agenda(agenda.getName(), votingSession);
    }

    public static AgendaDTO mapToDTO(Agenda agenda) {
        List<VotingSessionDTO> votingSession = VotingSessionMapper.mapEntityCollectionToDTO(agenda.getVotingSession());
        return new AgendaDTO(agenda.getId(), agenda.getName(), votingSession);
    }
    
}
