package com.github.thiagodutra.coopvoteservice.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coop")
public class VoteController {

    @GetMapping
    public String getVote() {
        return "It works";
    }

    @PostMapping("/vote")
    public String name() {
        return "Post works";
    }
}