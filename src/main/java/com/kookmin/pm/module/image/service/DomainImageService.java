package com.kookmin.pm.module.image.service;

import com.kookmin.pm.module.image.domain.ImageName;
import com.kookmin.pm.module.image.repository.ImageRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DomainImageService {
    private final FileUploadService fileUploadService;
    private final ImageRepository imageRepository;

    public String uploadImage(@NonNull Long relatedId, @NonNull MultipartFile file) {
        String imageUrl = this.fileUploadService.uploadImage(file);

        ImageName imageName = ImageName.builder()
                .name(imageUrl)
                .relatedId(relatedId)
                .build();

        imageRepository.save(imageName);

        return imageUrl;
    }

    public List<String> getImageUrl(@NonNull Long relatedId) {
        List<ImageName> imageNameList = this.imageRepository.findByRelatedId(relatedId);
        List<String> urlList = new ArrayList<>();

        for(ImageName imageName : imageNameList) {
            urlList.add(imageName.getName());
        }

        return urlList;
    }
}
