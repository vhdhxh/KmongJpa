package com.talentmarket.KmongJpa.entity;

import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;



@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Cart {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    private int count;

    public static Cart createCart(Board board,int count ,PrincipalDetails principalDetails) {
        return Cart.builder()
                .count(count)
                .user(principalDetails.getDto())
                .board(board)
                .build();
    }
    public void updateCount(int count){
        this.count +=count;
    }
}
