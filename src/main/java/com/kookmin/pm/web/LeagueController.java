package com.kookmin.pm.web;

import com.kookmin.pm.module.league.dto.LeagueCreateInfo;
import com.kookmin.pm.module.league.dto.LeagueEditInfo;
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

    @PutMapping(value = "/league/{leagueId}")
    public ResponseEntity editLeague(Principal principal,
                                     @PathVariable(name = "leagueId") Long leagueId,
                                     @RequestBody LeagueEditInfo leagueEditInfo) {
        Long usn = getPrincipalKey(principal);

        leagueEditInfo.setId(leagueId);
        leagueService.editLeague(usn, leagueEditInfo);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("리그 정보를 수정하였습니다.");
    }

    @DeleteMapping(value = "/league/{leagueId}")
    public ResponseEntity deleteLeague(Principal principal,
                                       @PathVariable(name = "leagueId") Long leagueId) {
        Long usn = getPrincipalKey(principal);
        leagueService.deleteLeague(usn, leagueId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("리그를 삭제하셨습니다.");
    }

    @PostMapping(value = "/league/participate/{leagueId}")
    public ResponseEntity participateLeague(Principal principal,
                                            @PathVariable(name = "leagueId") Long leagueId) {
        Long usn = getPrincipalKey(principal);
        leagueService.participateLeague(usn, leagueId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("대회에 참여 신청을 했습니다.");
    }

    @PutMapping(value = "/league/participate/approve/{requestId}")
    public ResponseEntity approveParticipationRequest(Principal principal,
                                                      @PathVariable(name="requestId") Long requestId) {
        Long usn = getPrincipalKey(principal);
        leagueService.approveParticipationRequest(usn, requestId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("리그 참가요청을 승인하셨습니다.");
    }

    @DeleteMapping(value = "/league/participate/reject/{requestId}")
    public ResponseEntity rejectParticipationRequest(Principal principal,
                                                     @PathVariable(name = "requestId") Long requestId) {
        Long usn = getPrincipalKey(principal);
        leagueService.rejectParticipationRequest(usn, requestId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("리그 참가요청을 거절하셨습니다.");
    }

    @DeleteMapping(value = "/league/participate/cancel/{requestId}")
    public ResponseEntity cancelParticipationRequest(Principal principal,
                                                     @PathVariable(name = "requestId") Long requestId) {
        Long usn = getPrincipalKey(principal);
        leagueService.quitParticipationRequest(usn, requestId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("리그 참가요청을 취소하셨습니다.");
    }

    @DeleteMapping(value = "/league/participate/{leagueId}")
    public ResponseEntity quitParticipation(Principal principal,
                                            @PathVariable(name="leagueId") Long leagueId) {
        Long usn = getPrincipalKey(principal);
        leagueService.quitParticipation(usn, leagueId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("리그를 탈퇴하셨습니다.");
    }

    private Long getPrincipalKey(Principal principal) {
        return Long.parseLong(principal.getName());
    }
}
