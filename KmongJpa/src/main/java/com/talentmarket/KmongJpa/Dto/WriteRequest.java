package com.talentmarket.KmongJpa.Dto;

import com.talentmarket.KmongJpa.entity.Users;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

@Getter
@Setter
@Builder
public class WriteRequest {
    private String title;
    private String contents;
    private String thumbnail;
    private String price;
    private String detail;
}
