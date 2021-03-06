package com.github.thiagodutra.coopvoteservice.domain.messages;

public class ApplicationMessages {

    private ApplicationMessages(){}

	public static final String VOTING_SESSION_IS_CLOSED = "The session is closed.";
	public static final String VOTING_SESSION_DOES_NOT_EXISTS = "Voting session with id: %d, does not exist.";
	public static final String AGENDA_DOES_NOT_EXISTS = "Agenda with id: %d, does not exist.";
	public static final String VOTE_COMPUTED = "Vote computed.";
	public static final String CANT_VOTE = "You cannot vote.";
	public static final String RESULT_IS_YES = "Option 'YES' WON";
	public static final String RESULT_IS_NO = "Option 'NO' WON.";
	public static final String RESULT_IS_DRAW = "Is a draw.";
	public static final String VOTING_SESSION_IS_NOT_FINISHED = "The voting session is not close.";
	public static final String DOCUMENT_NOT_FOUND = "Document not found.";
	public static final String DOCUMENT_UNABLE_TO_VOTE = "The document is invalid.";
	public static final String DOCUMENT_ALREADY_VOTED = "The document number already voted at this topic";
	public static final String SOMETHING_WEIRD_OCCURRED = "Something weird occurred, please check the logs.";
}