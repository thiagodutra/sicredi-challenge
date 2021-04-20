package com.github.thiagodutra.coopvoteservice.domain.service.document;

import com.github.thiagodutra.coopvoteservice.domain.dto.UserDocumentInfoResponse;
import com.github.thiagodutra.coopvoteservice.domain.exceptions.InvalidDocumentNumberException;
import com.github.thiagodutra.coopvoteservice.domain.messages.ApplicationMessages;
import com.github.thiagodutra.coopvoteservice.domain.service.DocumentService;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class DocumentServiceImpl implements DocumentService{

	private WebClient webClient;

	public boolean validateDocument(String document) {
		UserDocumentInfoResponse response;
		webClient = WebClient.create();
		try {
			response = webClient.get()
			.uri(String.format("https://user-info.herokuapp.com/users/%s", document))
			.retrieve()
			.bodyToMono(UserDocumentInfoResponse.class)
			.block();
			if (response != null){
				return response.isAbleToVote();
			}
			return false;
		} catch (HttpClientErrorException httpClientErrorException) {
			throw new InvalidDocumentNumberException(ApplicationMessages.DOCUMENT_UNABLE_TO_VOTE);
		}
	}
}