package com.kookmin.pm.module.image.service;

import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.InputStream;

public interface S3Service {
    public void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String fileName);
    public String getFileUrl(String fileName);
}
