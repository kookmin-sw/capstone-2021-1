package com.kookmin.pm.module.image.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service{
    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket")
    private final String bucket;

    @Override
    public void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String fileName) {
        try{
            byte[] bytes = IOUtils.toByteArray(inputStream);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

            PutObjectRequest imageUploadRequest = new PutObjectRequest(bucket, fileName, byteArrayInputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3Client.putObject(imageUploadRequest);
        } catch (IOException exception) {
            throw new RuntimeException();
        }
    }

    @Override
    public String getFileUrl(String fileName) {
        return String.valueOf(amazonS3Client.getUrl(bucket, fileName));
    }
}
