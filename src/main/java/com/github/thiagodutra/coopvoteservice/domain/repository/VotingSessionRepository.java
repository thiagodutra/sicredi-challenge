package com.github.thiagodutra.coopvoteservice.domain.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.github.thiagodutra.coopvoteservice.domain.entities.VotingSession;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VotingSessionRepository extends JpaRepository<VotingSession, Long>{


//     @Query("SELECT u FROM User u WHERE u.status = :status and u.name = :name")
// User findUserByStatusAndNameNamedParams(
//   @Param("status") Integer status, 
//   @Param("name") String name);

    List<VotingSession> findBySessionName (String sessionName);
    List<VotingSession> findByAlreadyProcessedFalseAndEndingVoteTimeGreaterThan(LocalDateTime endingVoteTime);
    // @NamedQuery("SELECT vs FROM tb_voting_session vs WHERE vs.endingVoteTime < :endingVoteTime and vs.already_processed = ")
    @Query("SELECT vs from TB_VOTING_SESSION vs WHERE vs.endingvotingtime >= :endingVotingTime AND vs.already_processed = :status ")
    List<VotingSession> findAllByEndingVotingTimeGreaterThanAndStatusEquals(@Param ("endingVotingTime") LocalDateTime endingVotingTime, @Param("status") Boolean status);

    
}
