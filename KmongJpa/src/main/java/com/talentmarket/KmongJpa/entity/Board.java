package com.talentmarket.KmongJpa.entity;

import com.talentmarket.KmongJpa.Dto.WriteRequest;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.event.EventListener;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)

public class Board {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long Id;
    private String title;
    private String contents;
    private String thumbnail;
    private String price;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users user;

    @CreatedDate
    private LocalDateTime createdDate;
    private String writer;
    private String detail;


    public static Board createBoard(WriteRequest reqeust, PrincipalDetails principalDetails) {
        return Board.builder()
                .user(principalDetails.getDto())
                .writer(principalDetails.getDto().getName())
                .thumbnail(reqeust.getThumbnail())
                .detail(reqeust.getDetail())
                .title(reqeust.getTitle())
                .price(reqeust.getPrice())
                .contents(reqeust.getContents())
                .build();
    }
}
