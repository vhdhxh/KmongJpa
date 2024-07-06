package com.talentmarket.KmongJpa.Item.application;

import com.talentmarket.KmongJpa.image.ImageResizer;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
@RequiredArgsConstructor
public class UploadService {
//    private final Uploader uploader;
    private final ImageResizer imageResizer;
    public static String PATH ="C:\\Users\\윤민수\\Downloads\\kmongimage\\";
    public void fileUpload(List<MultipartFile> files) {
//        File file = new File(PATH);


        files.forEach(file -> {
            try {
               InputStream is =  imageResizer.resize(file,100,100);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            CompletableFuture.runAsync(()->{try {
                file.transferTo(new File(PATH + file.getOriginalFilename()));
            } catch (IOException  e) {
                throw new RuntimeException(e);
            }}).exceptionally(e->{
                e.printStackTrace();
                return null;
            });
        });
    }


    @Async
    public void normalUpload(List<MultipartFile> files) throws IOException {
//        files.forEach(file -> {
//            try {
//                file.transferTo(new File(PATH + file.getOriginalFilename()));
//             Thread.sleep(3000);
//            } catch (IOException | InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        });
//        files.forEach(file -> {
//            try {
////                uploader.uploadImage(file);
//
//            } catch (IOException  e) {
//                throw new RuntimeException(e);
//            }
//        });



    }



}
