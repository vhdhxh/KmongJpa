package com.talentmarket.KmongJpa.Item.application;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static com.talentmarket.KmongJpa.Item.application.UploadService.PATH;

@Component
public class Uploader {
//    @Async
    public void uploadImage(MultipartFile file) throws IOException {
        file.transferTo(new File(PATH + file.getOriginalFilename()));
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }
}
