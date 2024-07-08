package com.talentmarket.KmongJpa.Item.application;

import com.talentmarket.KmongJpa.image.ImageResizer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;


@Service
@RequiredArgsConstructor
public class UploadService {
    private final Uploader uploader;
    private final ImageResizer imageResizer;

    private final Executor executor;
    public static String PATH ="C:\\Users\\윤민수\\Downloads\\kmongimage\\";
    public void fileUpload(List<MultipartFile> files) {
//        File file = new File(PATH);

        files.forEach(file -> {
            try {
                Long startTime = System.currentTimeMillis();
               imageResizer.resize(file,50,50,PATH);
                Long entTime = System.currentTimeMillis();
                System.out.println(" duration : " + (startTime - entTime));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//            CompletableFuture.runAsync(()->{try {
//                file.transferTo(new File(PATH + file.getOriginalFilename()));
//                System.out.println(file.getOriginalFilename());
//            } catch (IOException  e) {
//                throw new RuntimeException(e);
//            }} ,executor).exceptionally(e->{
//                e.printStackTrace();
//                return null;
//            });
        });
    }


//    @Async
    public void normalUpload(List<MultipartFile> files) throws IOException {
//        files.forEach(file -> {
//            try {
//                file.transferTo(new File(PATH + file.getOriginalFilename()));
//             Thread.sleep(3000);
//            } catch (IOException | InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        });
        files.forEach(file -> {
            try {
                uploader.uploadImage(file);

            } catch (IOException  e) {
                throw new RuntimeException(e);
            }
        });



    }



}
