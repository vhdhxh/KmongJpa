package com.talentmarket.KmongJpa.entity;

import com.talentmarket.KmongJpa.Dto.WriteRequest;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.exception.CustomException;
import com.talentmarket.KmongJpa.exception.ErrorCode;
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

    public static Item createBoard(WriteRequest request, PrincipalDetails principalDetails) {
        Users user = principalDetails.getDto();
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
       int restStock = this.stockQuantity-quantity;
       if (restStock<0) {
           throw new CustomException(ErrorCode.STOCK_NOT_NEGATIVE);
       }
    }
}
