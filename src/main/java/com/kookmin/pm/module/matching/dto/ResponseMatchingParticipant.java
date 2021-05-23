package com.kookmin.pm.module.matching.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseMatchingParticipant {
    private MatchingDetails matching;
    private List<MatchingParticipantDetails> request;
}
