package com.github.thiagodutra.coopvoteservice.domain.service.tasks;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.github.thiagodutra.coopvoteservice.domain.entities.Vote;
import com.github.thiagodutra.coopvoteservice.domain.entities.VotingSession;
import com.github.thiagodutra.coopvoteservice.domain.messages.ApplicationMessages;
import com.github.thiagodutra.coopvoteservice.domain.rabbit.VotingResultProducer;
import com.github.thiagodutra.coopvoteservice.domain.repository.VoteRepository;
import com.github.thiagodutra.coopvoteservice.domain.service.VotingSessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import lombok.extern.slf4j.Slf4j;

@Component
@EnableScheduling
@Slf4j
@EnableAsync
public class ClosedSessionScanService {

    @Autowired private VotingSessionService votingSessionService;
    @Autowired private VoteRepository voteRepository;
    @Autowired private VotingResultProducer votingResultProducer;

    @Async
    @Scheduled(cron = "10 * * * * *")
    public void searchForClosedSessionToProcess(){
        LocalDateTime now = LocalDateTime.now();
        log.info("Initiated ClosedSessionScanService at: {}", now.toString());
        log.debug("Searching for VotingSession with endingVotingTime less than: {}", now.toString());
        List<VotingSession> votingSessions = votingSessionService.findClosedSessionsToProcess(now);
        if (CollectionUtils.isEmpty(votingSessions)){
            log.info("No VotingSession able to be processed was found!!");
        } else {
            log.info("Fetching not processed closed sessions from DataBase");
            Map<Long, String> results = this.calculateResults(votingSessions);
            this.sendResults(results);
        }
        log.info("Wait 'til next scan");
        
    }

    private Map<Long, String>  calculateResults(List<VotingSession> votingSessions) {
        Map<Long, String> votingSessionMap = new HashMap<>();
        votingSessions.forEach(session -> {
            log.debug("Session ID: {}, Session Name: {}, Session EndingTime: {}", session.getId(), session.getSessionName(), session.getEndingVoteTime().toString());
            List<Vote> votes = voteRepository.findAllByVotingSession(session);
            if(CollectionUtils.isEmpty(votes)){
                log.info("{} - No votes was computed for the VotingSession: {}", LocalTime.now().toString(), session.getSessionName());
                votingSessionMap.put(session.getId(), "No votes were computed for this VotingSession");
            } else { //I Need this else to not check again if votes size is zero, to prevent a division by 0
                String result = calculateResult(voteRepository.findAllByVotingSession(session));
                votingSessionMap.put(session.getId(), result);
            }
        });

        return votingSessionMap;
    }

    private String calculateResult(List<Vote> votes){
        log.info("Starting to count the YES votes");
        int voteYes = votes.stream().filter(vote -> vote.getVote().equalsIgnoreCase("yes"))
            .collect(Collectors.toList()).size();
        BigDecimal voteYesPercentage = BigDecimal.valueOf(voteYes)
            .divide(BigDecimal.valueOf(votes.size()), 2, RoundingMode.HALF_UP);
        log.info("Calculated the percentage of YES VOTES");
        int result = voteYesPercentage.compareTo(BigDecimal.valueOf(0.50));
        log.info("Determining the results");
        if(result < 0) {
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
        results.entrySet().forEach(session -> 
            votingResultProducer.produceMessage(String.format("The result for Session ID:%d is %s", session.getKey(), session.getValue())
        ));
    }
}
