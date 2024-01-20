package com.talentmarket.KmongJpa.Dto;

import com.talentmarket.KmongJpa.entity.Users;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

@Getter
@Builder
public class WriteRequest {
    private String title;
    private String contents;
    private String thumbnail;
    private String price;
    private String writer;
    private String detail;
}
