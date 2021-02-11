package com.kookmin.pm.web;

import com.kookmin.pm.module.category.domain.Category;
import com.kookmin.pm.module.category.repository.CategoryRepository;
import com.kookmin.pm.module.crew.dto.CrewCreateInfo;
import com.kookmin.pm.module.crew.dto.CrewDetails;
import com.kookmin.pm.module.crew.service.CrewLookupType;
import com.kookmin.pm.module.crew.service.CrewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

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
}
