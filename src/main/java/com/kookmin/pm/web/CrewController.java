package com.kookmin.pm.web;

import com.kookmin.pm.module.category.repository.CategoryRepository;
import com.kookmin.pm.module.crew.dto.CrewCreateInfo;
import com.kookmin.pm.module.crew.dto.CrewDetails;
import com.kookmin.pm.module.crew.service.CrewLookupType;
import com.kookmin.pm.module.crew.service.CrewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class CrewController {
    private final CrewService crewService;
    private final CategoryRepository categoryRepository;

    @GetMapping(value = "/crew/detail/{crewId}")
    public ResponseEntity<CrewDetails> lookupCrew(@PathVariable(name = "crewId") Long crewId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(crewService.lookupCrew(crewId, CrewLookupType.WITH_PARTICIPANTS));
    }

    @PostMapping(value = "/crew")
    public ResponseEntity<String> establishCrew(Principal principal,
                                                @RequestBody CrewCreateInfo crewCreateInfo) {
        Long usn = Long.parseLong(principal.getName());
        crewService.establishCrew(usn, crewCreateInfo);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("크루가 생성되었습니다.");
    }

    @PostMapping(value = "/crew/participate/{crewId}")
    public ResponseEntity<String> participateCrew(Principal principal,
                                                  @PathVariable(name="crewId") Long crewId) {
        Long usn = Long.parseLong(principal.getName());
        crewService.participateCrew(usn, crewId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("크루 참여를 요청했습니다.");
    }

    @PutMapping(value = "/crew/participate/approve/{requestId}")
    public ResponseEntity<String> approveParticipation(Principal principal,
                                                       @PathVariable(name = "requestId") Long requestId) {
        Long usn = Long.parseLong(principal.getName());
        crewService.approveParticipationRequest(usn, requestId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("크루 참가 요청을 승인했습니다.");
    }

    @DeleteMapping(value = "/crew/participate/reject/{requestId}")
    public ResponseEntity<String> rejectParticipation(Principal principal,
                                                      @PathVariable(name="requestId") Long requestId) {
        Long usn = Long.parseLong(principal.getName());
        crewService.rejectParticipationRequest(usn, requestId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("크루 참가 요청을 거절했습니다.");
    }

    @DeleteMapping(value="/crew/participate/cancel/{requestId}")
    public ResponseEntity<String> quitParticipation(Principal principal,
                                                    @PathVariable(name="requestId") Long requestId) {
        Long usn = Long.parseLong(principal.getName());
        crewService.cancelParticipation(usn, requestId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("크루 참가 요청을 거절했습니다.");
    }
}
