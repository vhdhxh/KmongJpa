package com.talentmarket.KmongJpa.image;

import com.talentmarket.KmongJpa.Item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class LocalUploadService implements ImageUploadService {
    private final ItemRepository itemRepository;

    @Value("UPLOAD_PATH")
    private String path;

    //파일업로드
    //파일 불러오기

    //1. 서버에 파일 생성
    @Override
    public String upload(MultipartFile multipartFile) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        String file = UUID.randomUUID() + now.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        multipartFile.transferTo(new File(path+file));
        return file;
    }



}
