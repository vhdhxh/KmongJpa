package com.talentmarket.KmongJpa.image;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageUploadService {
    public String upload(MultipartFile multipartFile) throws IOException;
}
