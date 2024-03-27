package com.talentmarket.KmongJpa.chat.repository;

import com.talentmarket.KmongJpa.chat.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat,Long> , ChatRepositoryCustom {


    List<Chat> findByChatRoomId(Long chatRoomId);

//    @Query("select c from Chat c where c.chatRoom in (:chatRoomIdList)")
//    List<Chat> findChatList (@Param("chatRoomIdList") List<Long> chatRoomIdList);
}
