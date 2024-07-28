package com.talentmarket.KmongJpa.Item.application;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.util.IOUtils;
import com.talentmarket.KmongJpa.image.ImageResizer;
import com.talentmarket.KmongJpa.image.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import static java.util.stream.Collectors.toList;


@Service
@RequiredArgsConstructor
public class UploadService {
    private final Uploader uploader;
    private final ImageResizer imageResizer;
    private final S3Uploader s3Uploader;

    private final Executor executor;
    public static String PATH ="C:\\Users\\윤민수\\Downloads\\kmongimage\\";




    public List<String> fileUpload(List<MultipartFile> files) {
        List<CompletableFuture<String>> futures = files.stream()
                .map(file -> CompletableFuture.supplyAsync(() -> {
                    try {
                        String uploadName = createUploadName(file);
                        InputStream inputStream = imageResizer.resize(file, 50, 50);
                        ObjectMetadata metaData = new ObjectMetadata();
                        InputStream newInputStream = setMetadata(metaData, inputStream, file.getContentType());
                        String url = s3Uploader.upload(uploadName, newInputStream, metaData);
                        System.out.println(url);
                        return url;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }, executor).exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                }))
                .collect(toList());

        // 모든 Future의 결과를 기다리고 결과를 리스트로 수집
        return futures.stream()
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .collect(toList());
    }


//    @Async
    public List<String> normalUpload(List<MultipartFile> files) throws IOException {

        List<String> urls = files.stream().map(file -> {
            try {
                String uploadName = createUploadName(file);
                InputStream inputStream = imageResizer.resize(file,50,50);
                ObjectMetadata metaData = new ObjectMetadata();
                InputStream newInputStream = setMetadata(metaData,inputStream,file.getContentType());
               String url = s3Uploader.upload(uploadName,newInputStream ,metaData);
               return url;
            } catch (IOException  e) {
                throw new RuntimeException(e);
            }
        }).collect(toList());
        return urls;
    }
    private String createUploadName(MultipartFile file) {
        String uuid = UUID.randomUUID().toString();
        String originalFileName = file.getOriginalFilename();
        String uploadName = "image/" + uuid + "_" +System.currentTimeMillis()+"_"+originalFileName ;
        return uploadName;
    }
    private InputStream setMetadata(ObjectMetadata metaData,InputStream inputStream, String contentType) throws IOException {
        byte[] bytes = IOUtils.toByteArray(inputStream);
        long contentLength = bytes.length;
        System.out.print("content" + "=" + contentLength);
        metaData.setContentLength(contentLength);
        metaData.setContentType(contentType);
        InputStream newInputStream = new ByteArrayInputStream(bytes);

        return newInputStream;
    }


}
