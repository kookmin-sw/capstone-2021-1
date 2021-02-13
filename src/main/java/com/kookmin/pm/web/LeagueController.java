package com.kookmin.pm.web;

import com.kookmin.pm.module.league.dto.LeagueCreateInfo;
import com.kookmin.pm.module.league.service.LeagueLookupType;
import com.kookmin.pm.module.league.service.LeagueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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

    @PostMapping(value = "/league")
    public ResponseEntity openLeague(Principal principal,
                                     @RequestBody LeagueCreateInfo leagueCreateInfo) {
        Long usn = getPrincipalKey(principal);
        leagueService.openLeague(usn, leagueCreateInfo);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("대회를 생성했습니다.");
    }

    private Long getPrincipalKey(Principal principal) {
        return Long.parseLong(principal.getName());
    }
}