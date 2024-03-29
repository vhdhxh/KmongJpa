package com.talentmarket.KmongJpa.chat.domain;

import com.talentmarket.KmongJpa.user.domain.Users;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Users user1;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Users user2;


    public static ChatRoom createRoom (Users user1 , Users user2) {
        ChatRoom chatRoom = ChatRoom
                .builder()
                .user1(user1)
                .user2(user2)
                .build();
        return chatRoom;
    }

//    @Builder.Default
//    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
//    private List<Chat> chatList = new ArrayList<>();
}
