package com.talentmarket.KmongJpa.image;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Component
public class ImageResizer {
    public void resize(MultipartFile multipartFile, int width, int height,String outputFilePath) throws IOException {
        try (InputStream originalInputStream = multipartFile.getInputStream();
             OutputStream outputStream = new FileOutputStream(outputFilePath + multipartFile.getOriginalFilename())) {

//             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Thumbnails.of(originalInputStream)
                    .size(width, height)
                    .outputFormat("jpg")
                    .toOutputStream(outputStream);
//            byte[] resizedImageBytes = outputStream.toByteArray();
//
//            return new ByteArrayInputStream(resizedImageBytes);
        }
    }
}
