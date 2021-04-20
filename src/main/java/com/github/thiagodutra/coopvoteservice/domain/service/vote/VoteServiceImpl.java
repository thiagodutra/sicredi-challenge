package com.github.thiagodutra.coopvoteservice.domain.service.vote;

import com.github.thiagodutra.coopvoteservice.domain.dto.VoteDTO;
import com.github.thiagodutra.coopvoteservice.domain.entities.Vote;
import com.github.thiagodutra.coopvoteservice.domain.entities.VotingSession;
import com.github.thiagodutra.coopvoteservice.domain.exceptions.ClosedVotingSessionException;
import com.github.thiagodutra.coopvoteservice.domain.exceptions.VoteNotSupportedException;
import com.github.thiagodutra.coopvoteservice.domain.messages.ApplicationMessages;
import com.github.thiagodutra.coopvoteservice.domain.repository.VoteRepository;
import com.github.thiagodutra.coopvoteservice.domain.service.DocumentService;
import com.github.thiagodutra.coopvoteservice.domain.service.VoteService;
import com.github.thiagodutra.coopvoteservice.domain.service.VotingSessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VoteServiceImpl implements VoteService {


    @Autowired
    VoteRepository voteRepository;
    @Autowired
    VotingSessionService votingSessionService;
    @Autowired
    DocumentService documentService;

    @Override
    public VoteDTO processVote(Long voteSessionId, VoteDTO voteDto){
        log.info("Starting to process user's vote...");
        Vote vote = voteDto.mapToEntity();
        VotingSession votingSession = votingSessionService.findById(voteSessionId).mapToEntity();
        validateIfSessionIsOpen(vote, votingSession);
        if (userCanVote(vote.getCpf())) {
            return computeVote(voteDto.mapToEntity(), votingSession).mapToDTO();
        }
        log.error("Something wrong occurred during vote processing");
        throw new VoteNotSupportedException(ApplicationMessages.SOMETHING_WEIRD_OCCURRED);       
    }

    private boolean userCanVote (String cpf){
        log.info("Validating document...");
        return documentService.validateDocument(cpf);
    }
    
    private void validateIfSessionIsOpen(Vote vote, VotingSession votingSession) {
        if (vote.getTimeOfVote().isAfter(votingSession.getEndingVoteTime())) {
            log.debug(String.format("VotingSession is closed. VotingSession starting time:%s. VoteTime:%s.", 
            votingSession.getEndingVoteTime().toString(), 
            vote.getTimeOfVote().toString()));
            throw new ClosedVotingSessionException(ApplicationMessages.VOTING_SESSION_IS_CLOSED);
        }
        log.info("User voted in time.");
    }

    private Vote computeVote(Vote vote, VotingSession votingSession){
        vote.setVotingSession(votingSession);
        try {
            return voteRepository.save(vote);
        } catch (DataIntegrityViolationException sqlException) {
            log.error("Cpf already voted:%s", vote.getCpf());
            throw new VoteNotSupportedException(ApplicationMessages.DOCUMENT_ALREADY_VOTED);
        }
    }
}