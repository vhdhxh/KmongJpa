package com.talentmarket.KmongJpa.Item.presentation;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class FileDto {
    private String name;
    private List<MultipartFile> files;
}
