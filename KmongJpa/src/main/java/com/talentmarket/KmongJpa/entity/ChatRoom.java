package com.talentmarket.KmongJpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Getter
public class ChatRoom {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatRoom_id")
    private Long id;


    public static ChatRoom createRoom () {
        ChatRoom chatRoom = ChatRoom
                .builder()
                .build();
        return chatRoom;
    }

//    @Builder.Default
//    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
//    private List<Chat> chatList = new ArrayList<>();
}
