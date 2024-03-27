package com.talentmarket.KmongJpa.Item.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name = "Itemcount")
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Table(name = "Itemcount")
public class Itemcount {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemcount_id")
    private Long Id;

    private Long itemcount;
}
