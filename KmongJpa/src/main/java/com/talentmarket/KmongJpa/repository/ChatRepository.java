package com.talentmarket.KmongJpa.repository;

import com.talentmarket.KmongJpa.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat,Long> ,ChatRepositoryCustom{


    List<Chat> findByChatRoomId(Long chatRoomId);

//    @Query("select c from Chat c where c.chatRoom in (:chatRoomIdList)")
//    List<Chat> findChatList (@Param("chatRoomIdList") List<Long> chatRoomIdList);
}
