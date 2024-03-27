package com.talentmarket.KmongJpa.comment.domain;

import com.talentmarket.KmongJpa.comment.application.dto.CommentWriteDto;
import com.talentmarket.KmongJpa.global.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.Item.domain.Item;
import com.talentmarket.KmongJpa.user.domain.Users;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Getter
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;
    private String contents;
    @CreatedDate
    private LocalDateTime createdDate;


    public static Comment CreateComment(CommentWriteDto commentWriteDto , PrincipalDetails principalDetails , Item item) {
        return Comment.builder()
                .item(item)
                .contents(commentWriteDto.getContents())
                .users(principalDetails.getDto())
                .build();

    }

    public void update(String contents) {
        this.contents = contents;
    }
}
