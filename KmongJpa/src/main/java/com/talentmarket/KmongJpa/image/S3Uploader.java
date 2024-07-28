package com.talentmarket.KmongJpa.image;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class S3Uploader {
    private final AmazonS3 amazonS3;
    @Value("${s3.bucket}")
    private String bucket;


    public String upload(String uploadName , InputStream inputStream, ObjectMetadata metaData) throws IOException {
        amazonS3.putObject(bucket, uploadName,inputStream ,metaData);
        return amazonS3.getUrl(bucket, uploadName).toString();
    }

}
