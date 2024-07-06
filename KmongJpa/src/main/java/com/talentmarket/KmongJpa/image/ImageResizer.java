package com.talentmarket.KmongJpa.image;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class ImageResizer {
    public InputStream resize(MultipartFile multipartFile, int width, int height) throws IOException {
        try (InputStream originalInputStream = multipartFile.getInputStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Thumbnails.of(originalInputStream)
                    .size(width, height)
                    .outputFormat("jpg")
                    .toOutputStream(outputStream);
            byte[] resizedImageBytes = outputStream.toByteArray();


            return new ByteArrayInputStream(resizedImageBytes);
        }
    }
}
