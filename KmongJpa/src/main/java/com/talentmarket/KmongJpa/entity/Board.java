package com.talentmarket.KmongJpa.entity;

import com.talentmarket.KmongJpa.Dto.WriteRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.event.EventListener;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

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
    private String createdDate;
    private String writer;
    private String detail;

    public static Board createBoard(WriteRequest reqeust) {
        return Board.builder()
                .writer(reqeust.getWriter())
                .thumbnail(reqeust.getThumbnail())
                .detail(reqeust.getDetail())
                .title(reqeust.getTitle())
                .price(reqeust.getPrice())
                .contents(reqeust.getContents())
                .build();
    }
}
