package com.talentmarket.KmongJpa.Item.application;

import com.talentmarket.KmongJpa.Item.presentation.FileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
@RequiredArgsConstructor
public class UploadService {
    public static String PATH ="file:C:\\Users\\윤민수\\Downloads\\kmongimage\\";
    public void fileUpload(List<MultipartFile> files) {
//        File file = new File(PATH);
        ExecutorService executor = Executors.newFixedThreadPool(10);
        files.forEach(file -> {
            CompletableFuture.runAsync(()->{try {
                file.transferTo(new File(PATH + file.getName()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }},executor).exceptionally(e->{
                e.printStackTrace();
                return null;
            });
        });
        executor.shutdown();
    }

    public void normalUpload(List<MultipartFile> files) {
        files.forEach(file -> {
            try {
                file.transferTo(new File(PATH + file.getName()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
