package com.github.thiagodutra.coopvoteservice.domain.utils.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.github.thiagodutra.coopvoteservice.domain.dto.VoteDTO;
import com.github.thiagodutra.coopvoteservice.domain.entities.Vote;

public class VoteMapper {
    
    private VoteMapper(){}

    public static List<Vote> voteDtoCollectioToEntity(List<VoteDTO> votes) {
        return votes.stream().map(vote -> mapToEntity(vote))
        .collect(Collectors.toList());
    }

    public static List<VoteDTO> voteEntityCollectionToDTO(List<Vote> votes) {
        return votes.stream().map(vote -> mapToDTO(vote))
        .collect(Collectors.toList());
    }

    public static Vote mapToEntity(VoteDTO vote){
        return new Vote(vote.getCpf(), vote.getVote());
    }

    public static VoteDTO mapToDTO(Vote vote) {
        return new VoteDTO(vote.getId(), vote.getCpf(), vote.getVote());
    }

}
