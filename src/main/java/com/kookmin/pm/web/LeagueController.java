package com.kookmin.pm.web;

import com.kookmin.pm.module.league.service.LeagueLookupType;
import com.kookmin.pm.module.league.service.LeagueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LeagueController {
    private final LeagueService leagueService;

    @GetMapping(value = "/league/detail/{leagueId}")
    public ResponseEntity lookupLeague(@PathVariable(name = "leagueId") Long leagueId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(leagueService.lookupLeague(leagueId, LeagueLookupType.WITH_PARTICIPANTS));
    }
}
