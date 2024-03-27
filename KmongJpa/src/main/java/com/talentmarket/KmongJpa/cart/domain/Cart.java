package com.talentmarket.KmongJpa.cart.domain;

import com.talentmarket.KmongJpa.global.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.Item.domain.Item;
import com.talentmarket.KmongJpa.user.domain.Users;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;


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

    @Builder.Default
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();


    public void addCartItem(CartItem cartItem) {
        cartItems.add(cartItem);
        cartItem.setCart(this);

    }




    public static Cart createCart(Item item, int count , PrincipalDetails principalDetails) {
        return Cart.builder()

                .user(principalDetails.getDto())
//                .item(item)
                .build();
    }
//    public void updateCount(int count){
//        this.count +=count;
//    }
}
