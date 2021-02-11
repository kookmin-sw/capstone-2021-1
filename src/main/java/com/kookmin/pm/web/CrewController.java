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
}
