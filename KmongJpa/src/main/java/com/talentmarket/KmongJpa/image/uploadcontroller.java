package com.talentmarket.KmongJpa.image;

import com.talentmarket.KmongJpa.global.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class uploadcontroller {
    private final LocalUploadService localUploadService;

    @PostMapping("/upload")
    public ApiResponse upload(@RequestPart("file") MultipartFile multipartFile) throws IOException {
      localUploadService.upload(multipartFile);
        return ApiResponse.ok(null);
    }
}
