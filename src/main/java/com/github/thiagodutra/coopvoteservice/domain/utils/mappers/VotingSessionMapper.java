package com.github.thiagodutra.coopvoteservice.domain.utils.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.github.thiagodutra.coopvoteservice.domain.dto.VoteDTO;
import com.github.thiagodutra.coopvoteservice.domain.dto.VotingSessionDTO;
import com.github.thiagodutra.coopvoteservice.domain.entities.VotingSession;

public class VotingSessionMapper {

    private VotingSessionMapper(){}

    public static List<VotingSession> mapDtoCollectionToEntity(List<VotingSessionDTO> votes) {
        return votes.stream().map(votingSession -> mapToEntity(votingSession))
        .collect(Collectors.toList());
    }

    public static List<VotingSessionDTO> mapEntityCollectionToDTO(List<VotingSession> votes) {
        return votes.stream().map(votingSession -> mapToDTO(votingSession))
        .collect(Collectors.toList());
    }

    public static VotingSessionDTO mapToDTO(VotingSession votingSession){
        List<VoteDTO> votes = VoteMapper.voteEntityCollectionToDTO(votingSession.getVotes());
        return new VotingSessionDTO(votingSession.getId(), votingSession.getTopicName(), votingSession.getVotingDurationInMinutes(), votes);
    }
    public static VotingSession mapToEntity(VotingSessionDTO votingSession) {
        return new VotingSession(votingSession.getTopicName(), votingSession.getVotingDurationInMinutes());
    }
    
}
