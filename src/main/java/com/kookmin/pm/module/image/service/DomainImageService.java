package com.kookmin.pm.module.image.service;

import com.kookmin.pm.module.image.domain.ImageName;
import com.kookmin.pm.module.image.repository.ImageRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DomainImageService {
    private final FileUploadService fileUploadService;
    private final ImageRepository imageRepository;

    @Value("${cloud.aws.images.board-game}")
    private String defaultBoardGame;

    @Value("${cloud.aws.images.room-escape}")
    private String defaultRoomEscape;

    @Value("${cloud.aws.images.crime-scene}")
    private String defaultCrimeScene;

    @Value("${cloud.aws.images.member}")
    private String defaultMember;

    @Value("${cloud.aws.images.crew}")
    private String defaultCrew;

    public String uploadImage(@NonNull Long relatedId, @NonNull MultipartFile file) {
        String imageUrl = this.fileUploadService.uploadImage(file);

        ImageName imageName = ImageName.builder()
                .name(imageUrl)
                .relatedId(relatedId)
                .build();

        imageRepository.save(imageName);

        return imageUrl;
    }

    public List<String> getImageUrl(@NonNull Long relatedId, String domainType) {
        List<ImageName> imageNameList = this.imageRepository.findByRelatedId(relatedId);
        List<String> urlList = new ArrayList<>();

        for(ImageName imageName : imageNameList) {
            urlList.add(imageName.getName());
        }

        if(urlList.size()==0) {
            setDefaultImage(urlList, domainType);
        }

        return urlList;
    }

    private void setDefaultImage(List<String> urlList, String domainType) {
        switch (domainType) {
            case "MEMBER":
                urlList.add(defaultMember);
                break;
            case "BOARD GAME":
                urlList.add(defaultBoardGame);
                break;
            case "ROOM ESCAPE":
                urlList.add(defaultRoomEscape);
                break;
            case "CRIME SCENE":
                urlList.add(defaultCrimeScene);
                break;
            case "CREW":
                urlList.add(defaultCrew);
                break;
        }
    }
}
