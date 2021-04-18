package com.kookmin.pm.web;

import com.kookmin.pm.module.matchup.dto.MatchUpDetails;
import com.kookmin.pm.module.matchup.service.MatchUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MatchUpController {
    private final MatchUpService matchUpService;

    @GetMapping("/league/{leagueId}/match-up")
    public ResponseEntity getMatchUpList(@PathVariable(name = "leagueId") Long leagueId) {
        List<MatchUpDetails> matchUpDetailsList = matchUpService.getMatchUpListByLeague(leagueId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(matchUpDetailsList);
    }
}
