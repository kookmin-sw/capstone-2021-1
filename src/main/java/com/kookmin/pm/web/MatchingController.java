package com.kookmin.pm.web;

import com.kookmin.pm.module.matching.dto.MatchingCreateInfo;
import com.kookmin.pm.module.matching.service.MatchingLookUpType;
import com.kookmin.pm.module.matching.service.MatchingService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping(value = "/matching")
    public ResponseEntity openMatching(Principal principal,
                                       @RequestBody MatchingCreateInfo matchingCreateInfo) {
        Long usn = Long.parseLong(principal.getName());
        matchingService.openMatching(usn, matchingCreateInfo);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("매칭이 생성되었습니다.");
    }
}
