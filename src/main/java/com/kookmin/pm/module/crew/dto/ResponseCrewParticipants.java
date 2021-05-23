package com.kookmin.pm.module.crew.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseCrewParticipants {
    private CrewDetails crew;
    private List<CrewParticipantsDetails> request;
}
