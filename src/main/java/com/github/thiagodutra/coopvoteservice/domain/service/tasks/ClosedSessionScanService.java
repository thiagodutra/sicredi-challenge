package com.github.thiagodutra.coopvoteservice.domain.service.tasks;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.github.thiagodutra.coopvoteservice.domain.entities.Vote;
import com.github.thiagodutra.coopvoteservice.domain.entities.VotingSession;
import com.github.thiagodutra.coopvoteservice.domain.messages.ApplicationMessages;
import com.github.thiagodutra.coopvoteservice.domain.rabbit.VotingResultProducer;
import com.github.thiagodutra.coopvoteservice.domain.repository.VoteRepository;
import com.github.thiagodutra.coopvoteservice.domain.repository.VotingSessionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClosedSessionScanService {

    @Autowired private VotingSessionRepository votingSessionRepository;
    @Autowired private VoteRepository voteRepository;
    @Autowired private VotingResultProducer votingResultProducer;

    @Scheduled(cron = "2 * * * *")
    public void searchForClosedSessionToProcess(){
        List<VotingSession> votingSessions = votingSessionRepository.findByAlreadyProcessedFalseAndEndingVoteTimeGreaterThan(LocalDateTime.now());
        if (CollectionUtils.isEmpty(votingSessions)){
            log.info("No Closed VotingSession was found!!");
        }
        Map<Long, String> results = this.calculateResults(votingSessions);
        this.sendResults(results);
    }

    private Map<Long, String>  calculateResults(List<VotingSession> votingSessions) {
        Map<Long, String> votingSessionMap = new HashMap<>();
        votingSessions.forEach(session -> {
            List<Vote> votes = voteRepository.findAllByVotingSession(session);
            if(CollectionUtils.isEmpty(votes)){
                log.info("No votes was computed for the VotingSession: {}", session.getSessionName());
                votingSessionMap.put(session.getId(), "No votes were computed for this VotingSession");
            } else { //I Need this else to not check again if votes size is zero, to prevent a division by 0
                String result = calculateResult(voteRepository.findAllByVotingSession(session));
                votingSessionMap.put(session.getId(), result);
            }
        });

        return votingSessionMap;

        // for(VotingSession votingSession: votingSessions) {
        //     votes = voteRepository.findAllByVotingSession(votingSession);
        //     if (CollectionUtils.isEmpty(votes)){
        //         log.info("No votes was computed for the VotingSession: {}", votingSession.getSessionName());
        //         votingSessionMap.put(votingSession.getId(), "No votes were computed for this VotingSession");   
        //     } else {
        //         result = calculateResult(voteRepository.findAllByVotingSession(votingSession));
        //         votingSessionMap.put(votingSession.getId(), result);
        //     }
        //     //SEND RESULTS TO RABBIT
        // }
    }

    private String calculateResult(List<Vote> votes){
        int voteYes = votes.stream().filter(vote -> vote.getVote().equalsIgnoreCase("yes")).collect(Collectors.toList()).size();
        BigDecimal voteYesPercentage = BigDecimal.valueOf(voteYes)
            .divide(BigDecimal.valueOf(votes.size()), 2, RoundingMode.HALF_UP);
        int result = voteYesPercentage.compareTo(BigDecimal.valueOf(0.5));
        if( result < 0) {
            return ApplicationMessages.RESULT_IS_NO;
        } else if (result > 0) {
            return ApplicationMessages.RESULT_IS_YES;
        }
        return ApplicationMessages.RESULT_IS_DRAW;
    }

    private void sendResults(Map<Long, String> results){
        if (results.isEmpty()){
            log.info("There wasn't closed sessions to be processed, therefore nothing will be sent to Queue");
            return;
        }
        results.entrySet().forEach(session -> {
            votingResultProducer.produceMessage(String.format("The result for Session ID:%d is %s", session.getKey(), session.getValue()));
        });
    }
}
