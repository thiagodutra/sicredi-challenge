package com.github.thiagodutra.coopvoteservice.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DefaultErrorResponse {
    
    private String operation;
    private String error;
    private String description;

}
