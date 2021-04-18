package com.kookmin.pm.web;

import com.kookmin.pm.module.matchup.dto.MatchUpCreateInfo;
import com.kookmin.pm.module.matchup.dto.MatchUpDetails;
import com.kookmin.pm.module.matchup.service.MatchUpLookUpType;
import com.kookmin.pm.module.matchup.service.MatchUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/league/{leagueId}/match-up/{matchUpId}")
    public ResponseEntity lookUpMatchUp(@PathVariable(name = "leagueId") Long leagueId,
                                        @PathVariable(name = "matchUpId") Long matchUpId) {

        MatchUpDetails matchUpDetails = matchUpService.lookUpMatchUp(matchUpId, MatchUpLookUpType.WITH_ALL_INFOS);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(matchUpDetails);
    }

    @PostMapping("/league/{leagueId}/match-up/{matchUpId}")
    public ResponseEntity requestMatching (Principal principal,
                                           @PathVariable(name="leagueId") Long leagueId,
                                           @PathVariable(name="matchUpId") Long matchUpId,
                                           @RequestBody MatchUpCreateInfo matchUpCreateInfo) {
        Long usn = getPrincipalKey(principal);
        Long matchingId = matchUpService.startMatching(usn, matchUpId, matchUpCreateInfo);

        Map<String, Object> result = new HashMap<>();
        result.put("matchingId", matchingId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @PutMapping("/league/{leagueId}/match-up/{matchUpId}/matching")
    public ResponseEntity approveMatching (Principal principal,
                                           @PathVariable(name="leagueId") Long leagueId,
                                           @PathVariable(name="matchUpId") Long matchUpId) {
        Long usn = getPrincipalKey(principal);
        matchUpService.approveMatchUp(usn, matchUpId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("매칭이 성사되었습니다.");
    }

    @PutMapping("/league/{leagueId}/match-up/{matchUpId}/end")
    public ResponseEntity endMatchUp (Principal principal,
                                      @PathVariable(name="leagueId") Long leagueId,
                                      @PathVariable(name="matchUpId") Long matchUpId,
                                      @RequestBody Map<String, Object> requestBody) {
        Long usn = getPrincipalKey(principal);
        Long winnerUsn = (Long)requestBody.get("winnerUsn");
        Long loserUsn = (Long)requestBody.get("loserUsn");

        matchUpService.endMathUp(winnerUsn, loserUsn, matchUpId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("매치업이 종료되었습니다.");
    }



    private Long getPrincipalKey(Principal principal) {
        return Long.parseLong(principal.getName());
    }
}
