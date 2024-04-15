package com.talentmarket.KmongJpa.Item.domain;

import com.talentmarket.KmongJpa.Item.application.dto.WriteRequest;
import com.talentmarket.KmongJpa.comment.domain.Comment;
import com.talentmarket.KmongJpa.user.domain.Users;
import com.talentmarket.KmongJpa.global.exception.CustomException;
import com.talentmarket.KmongJpa.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long Id;
    private String title;
    private String contents;
    private String thumbnail;
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @Builder.Default
    @OneToMany(mappedBy = "item")
    private List<Comment> comment = new ArrayList<>();


    @CreatedDate
    private LocalDateTime createdDate;
    private String writer;
    private String detail;
    private int stockQuantity;

    public static Item createBoard(WriteRequest request, Users user) {

        return Item.builder()
                .users(user)
                .writer(user.getName())
                .thumbnail(request.getThumbnail())
                .detail(request.getDetail())
                .title(request.getTitle())
                .price(request.getPrice())
                .contents(request.getContents())
                .build();
    }

    public Long updateBoard(WriteRequest request) {
        this.contents = request.getContents();
        this.detail = request.getDetail();
        this.price = request.getPrice();
        this.thumbnail = request.getThumbnail();
        this.title = request.getTitle();
        return this.Id;
    }

    public void stockReduce(int quantity) {
       stockQuantity = stockQuantity-quantity;
       if (stockQuantity<0) {
           throw new CustomException(ErrorCode.STOCK_NOT_NEGATIVE);
       }
    }
}
