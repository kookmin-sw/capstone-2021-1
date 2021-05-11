package com.kookmin.pm.module.image.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadService {
    private final S3Service s3Service;

    public String uploadImage(MultipartFile file) {
        String fileName = createFileName(file.getOriginalFilename());

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());

        try{
            InputStream inputStream = file.getInputStream();
            s3Service.uploadFile(inputStream, objectMetadata, fileName);

            //TODO::add image table and data

        } catch (IOException exception) {
            throw new IllegalArgumentException();
        }

        return s3Service.getFileUrl(fileName);
    }

    private String createFileName(String originalFileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(originalFileName));
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException exception) {
            throw new IllegalArgumentException();
        }
    }
}
