package com.talentmarket.KmongJpa.order.domain;

import com.talentmarket.KmongJpa.order.application.OrderStatus;
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
@Table(name = "orders")
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;
    @Builder.Default
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems=new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public void updateStatus(OrderStatus status) {
        this.orderStatus = status;
    }

    public static Order createOrder(Users user, String uuid) {
        return Order.builder()
                .orderStatus(OrderStatus.Try)
                .uuid(uuid)
                .user(user)
                .build();
    }


}
