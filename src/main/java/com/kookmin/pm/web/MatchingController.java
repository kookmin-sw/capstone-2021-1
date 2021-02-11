package com.kookmin.pm.web;

import com.kookmin.pm.module.matching.dto.MatchingCreateInfo;
import com.kookmin.pm.module.matching.dto.MatchingDetails;
import com.kookmin.pm.module.matching.dto.MatchingEditInfo;
import com.kookmin.pm.module.matching.dto.MatchingSearchCondition;
import com.kookmin.pm.module.matching.service.MatchingLookUpType;
import com.kookmin.pm.module.matching.service.MatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class MatchingController {
    private final MatchingService matchingService;

    @GetMapping(value = "/matching/detail/{matchingId}")
    public ResponseEntity lookupMatching(@PathVariable(name = "matchingId") Long matchingId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(matchingService.lookupMatching(matchingId,
                        MatchingLookUpType.WITH_PARTICIPANTS));
    }

    @GetMapping(value = "/matching/search")
    public ResponseEntity searchMatching(Pageable pageable, MatchingSearchCondition searchCondition) {
        Page<MatchingDetails> response = matchingService.searchMatching(pageable, searchCondition);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping(value = "/matching")
    public ResponseEntity openMatching(Principal principal,
                                       @RequestBody MatchingCreateInfo matchingCreateInfo) {
        Long usn = Long.parseLong(principal.getName());
        matchingService.openMatching(usn, matchingCreateInfo);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("매칭이 생성되었습니다.");
    }

    @PutMapping(value = "/matching/{matchingId}")
    public ResponseEntity editMatching(Principal principal,
                                       @PathVariable(name = "matchingId") Long matchingId,
                                       @RequestBody MatchingEditInfo matchingEditInfo) {
        Long usn = getPrincipalKey(principal);
        matchingEditInfo.setId(matchingId);

        System.out.println("TIME: " + matchingEditInfo.getStartTime());

        matchingService.editMatching(usn, matchingEditInfo);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("매칭 정보가 수정되었습니다.");
    }

    @DeleteMapping(value = "/matching/{matchingId}")
    public ResponseEntity quitMatching(Principal principal,
                                       @PathVariable(name = "matchingId") Long matchingId) {
        Long usn = getPrincipalKey(principal);
        matchingService.quitMatching(usn, matchingId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("매칭을 취소하셨습니다.");
    }

    @PostMapping(value = "/matching/participate/{matchingId}")
    public ResponseEntity participateMatching(Principal principal,
                                              @PathVariable(name = "matchingId") Long matchingId) {
        Long usn = getPrincipalKey(principal);
        matchingService.participateMatching(usn, matchingId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("매칭에 참가하였습니다.");
    }

    private Long getPrincipalKey(Principal principal) {
        return Long.parseLong(principal.getName());
    }

    @DeleteMapping(value = "/matching/participate/cancel/{requestId}")
    public ResponseEntity quitParticipationRequest(Principal principal,
                                                     @PathVariable(name="requestId") Long requestId) {
        Long usn = getPrincipalKey(principal);
        matchingService.quitParticipationRequest(usn, requestId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("매칭 참가 요청을 취소하였습니다.");
    }

    @DeleteMapping(value = "/matching/participate/reject/{requestId}")
    public ResponseEntity rejectParticipationRequest(Principal principal,
                                                     @PathVariable(name="requestId") Long requestId) {
        Long usn = getPrincipalKey(principal);
        matchingService.rejectParticipationRequest(usn, requestId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("매칭 참가 요청을 거절하습니다.");
    }

    @PutMapping(value = "/matching/participate/approve/{requestId}")
    public ResponseEntity approveParticipationRequest(Principal principal,
                                                      @PathVariable(name = "requestId") Long requestId) {
        Long usn = getPrincipalKey(principal);
        matchingService.approveParticipationRequest(usn, requestId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("매칭 참가 요청을 승낙하셨습낙니다.");
    }

    @DeleteMapping(value = "/matching/participate/{matchingId}")
    public ResponseEntity cancelParticipation(Principal principal,
                                            @PathVariable(name = "matchingId") Long matchingId) {
        Long usn = getPrincipalKey(principal);
        matchingService.cancelParticipation(usn, matchingId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("매칭 참가를 취소하셨습니다.");
    }

    @PutMapping(value = "/matching/{matchingId}/start")
    public ResponseEntity startMatching(Principal principal,
                                        @PathVariable(name = "matchingId") Long matchingId) {
        Long usn = getPrincipalKey(principal);
        matchingService.startMatching(usn, matchingId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("매칭을 시작합니다.");
    }

    @PutMapping(value = "/matching/{matchingId}/end")
    public ResponseEntity endMatching(Principal principal,
                                        @PathVariable(name = "matchingId") Long matchingId) {
        Long usn = getPrincipalKey(principal);
        matchingService.endMatching(usn, matchingId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("매칭을 종료합니다.");
    }
}
