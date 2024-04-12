package com.talentmarket.KmongJpa.Item.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class ItemImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_image_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    @Column(name = "imageName")
    private String imageName;
}
