package com.kookmin.pm.web;

import com.kookmin.pm.module.crew.service.CrewService;
import com.kookmin.pm.module.matching.service.MatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class ImageController {
    private final MatchingService matchingService;
    private final CrewService crewService;

    @PostMapping(value = "/matching/{matchingId}/image")
    public ResponseEntity uploadMatchingImage(Principal principal,
                                      @PathVariable(name = "matchingId") Long matchingId,
                                      @RequestPart MultipartFile file) {

        String imageUrl = this.matchingService.uploadMatchingImage(matchingId, file);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(imageUrl);
    }

    @PostMapping(value = "/crew/{crewId}/image")
    public ResponseEntity uploadCrewImages(Principal principal,
                                           @PathVariable(name = "crewId") Long crewId,
                                           @RequestPart MultipartFile file) {
        String imageUrl = this.crewService.uploadCrewImage(crewId, file);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(imageUrl);
    }
}
