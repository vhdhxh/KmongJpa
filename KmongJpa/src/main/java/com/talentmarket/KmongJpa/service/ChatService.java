package com.talentmarket.KmongJpa.service;


import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.entity.ChatRoom;
import com.talentmarket.KmongJpa.entity.Users;
import com.talentmarket.KmongJpa.repository.ChatRoomRepository;
import com.talentmarket.KmongJpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    //원래 플로우
    //문의하기 버튼을 누르면,채팅방 생성후, 채팅방 id를 넘겨준다.
    //바뀐 플로우
    //문의하기 버튼을 누르면, 상대와 나의 채팅 방이 있는지 확인하고, 없다면 만들어서 채팅방 id를 넘겨주고
    // 있다면 기존 채팅방 id를 넘겨준다.
    public Long createRoom(PrincipalDetails principalDetails, Long user2Id) {
        Long chatRoomId;
        Users user1 = principalDetails.getDto();
        Long user1Id = user1.getId();


        Optional<ChatRoom> chatRoom = chatRoomRepository.findByUser1IdAndUser2Id(user1Id, user2Id);
        if (chatRoom.isEmpty()) {
            chatRoom = chatRoomRepository.findByUser2IdAndUser1Id(user1Id, user2Id);
            if (chatRoom.isEmpty()){
                Users user2 = userRepository.findById(user2Id).get();
               ChatRoom entity = ChatRoom
                       .builder()
                       .user1(user1)
                       .user2(user2)
                       .build();
                 chatRoomId = chatRoomRepository.save(entity).getId();
                 return chatRoomId;
               }
        }
         chatRoomId = chatRoom.get().getId();
        return chatRoomId;
    }
}
