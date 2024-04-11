package com.talentmarket.KmongJpa.global;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class uploadcontroller {
    private final ImageUploadService imageUploadService;

    @PostMapping("/upload")
    public ApiResponse upload(@RequestPart("file") MultipartFile multipartFile) throws IOException {
      imageUploadService.upload(multipartFile);
        return ApiResponse.ok(null);
    }
}
