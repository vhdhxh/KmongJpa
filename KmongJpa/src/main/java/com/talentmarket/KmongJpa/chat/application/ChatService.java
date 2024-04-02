package com.talentmarket.KmongJpa.chat.application;


import com.talentmarket.KmongJpa.chat.application.dto.*;
import com.talentmarket.KmongJpa.chat.domain.ChatRoom;
import com.talentmarket.KmongJpa.chat.repository.ChatRepository;
import com.talentmarket.KmongJpa.chat.repository.ChatRoomRepository;
import com.talentmarket.KmongJpa.chat.domain.Chat;
import com.talentmarket.KmongJpa.user.domain.Users;
import com.talentmarket.KmongJpa.global.exception.CustomException;
import com.talentmarket.KmongJpa.global.exception.ErrorCode;
import com.talentmarket.KmongJpa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    //원래 플로우
    //문의하기 버튼을 누르면,채팅방 생성후, 채팅방 id를 넘겨준다.
    //바뀐 플로우
    //문의하기 버튼을 누르면, 상대와 나의 채팅 방이 있는지 확인하고, 없다면 만들어서 채팅방 id를 넘겨주고
    // 있다면 기존 채팅방 id를 넘겨준다.
    public Long createRoom(Users user, Long user2Id) {

        Long chatRoomId;
        Users user1 = user;
        Long user1Id = user1.getId();
        Optional<ChatRoom> chatRoom = chatRoomRepository.findChatUser(user1Id, user2Id);
        if (chatRoom.isEmpty()) {
            Users user2 = userRepository.findById(user2Id).get();
            ChatRoom chatRoom1 = ChatRoom.createRoom(user1, user2);
            chatRoomId = chatRoomRepository.save(chatRoom1).getId();
            return chatRoomId;
        }
        chatRoomId = chatRoom.get().getId();
        return chatRoomId;



    }

    public SendResponse sendMessage(SendRequest req, String nickName) {
        Long senderId = req.getSenderId();
        Long receiverId = req.getReceiverId();
        String message = req.getMessage();

        ChatRoom chatRoom = chatRoomRepository.findChatUser(senderId, receiverId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHATROOM_NOT_FOUND));
           Users user = userRepository.findById(senderId).get();
           String senderName = user.getName();
           SendResponse sendResponse = new SendResponse(senderName,message);
        Chat chat = Chat.createChat(chatRoom, message, senderId ,receiverId);

        chatRepository.save(chat);
        return sendResponse;
    }

    public List<ChatListResponse> getChatList(Users user) {
        Long userId = user.getId();
        log.info(String.valueOf(userId));
        //chatroomRepository 에서 유저의 모든 방 리스트 엔티티를 찾아온다.
        List<ChatRoom> chatRoomList = chatRoomRepository.findChatRoom(userId);
        log.info(chatRoomList.toString());
        //유저의 대화 상대 Id 리스트
        List<Long> partnerIdList = new ArrayList<>(); // 초기화 추가

        for (ChatRoom chatRoom : chatRoomList) {
            Long partnerId = chatRoom.getUser1().getId().equals(userId) ? chatRoom.getUser2().getId() : chatRoom.getUser1().getId();
            partnerIdList.add(partnerId);
        }

        List<Users> users = userRepository.findAllById(partnerIdList);
        Map<Long, String> userIdToNameMap =
                users.stream().collect(Collectors.toMap(Users::getId, Users::getName));

        List<Long> chatRoomIdList = chatRoomList.stream().map(ChatRoom::getId).collect(Collectors.toList());
        log.info(chatRoomIdList.toString());
        List<Chat> chats = chatRepository.findChatList(chatRoomIdList);
      log.info(chats.toString());
        List<ChatListResponse> chatListResponses = chats.stream().map(chat -> {
            Long partnerId = chat.getSenderId().equals(userId) ? chat.getReceiverId() : chat.getSenderId();


            String otherNickname = userIdToNameMap.get(partnerId);

            return ChatListResponse.builder()
                    .otherNickname(otherNickname)
                    .recentMessage(chat.getMessage())
                    .roomId(chat.getChatRoom().getId())
                    .build();
        }).toList();

        return chatListResponses;
    }
    public List<ChatResponse> getChat (Users user, ChatRequest req , Long roomId) {
        Long userId = user.getId();
        log.info("userId :{}",userId);
        Long partnerId = req.getPartnerId();
        String partnerName = req.getPartnerName();
        String userNickName = user.getName();
        log.info("userNickName:{}",userNickName);
        List<Chat> chatList = chatRepository.findByChatRoomId(roomId);
        Map<Long, String> userIdToNameMap = new HashMap<>();
        userIdToNameMap.put(userId, userNickName);
        userIdToNameMap.put(partnerId,partnerName);

         List<ChatResponse> chatResponses = chatList
                 .stream()
                 .map(c-> ChatResponse
                              .builder()
                              .roomId(roomId)
                              .senderNickname(userIdToNameMap.get(c.getSenderId()))
                              .receiverNickname(userIdToNameMap.get(c.getReceiverId()))
                              .message(c.getMessage())
                              .build()
                 ).collect(Collectors.toList());

        return chatResponses;
    }
}


