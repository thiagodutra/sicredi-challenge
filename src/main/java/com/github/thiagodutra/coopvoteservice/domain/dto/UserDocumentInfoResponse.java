package com.github.thiagodutra.coopvoteservice.domain.dto;

import lombok.Data;

@Data
public class UserDocumentInfoResponse {

    private static final String ABLE_TO_VOTE = "ABLE_TO_VOTE";
	
	private String status;

    public Boolean isAbleToVote() {
		if (this.status == null) {
			return false;
		}
		return getStatus().equalsIgnoreCase(ABLE_TO_VOTE);
	}

}
