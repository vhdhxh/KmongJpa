package com.talentmarket.KmongJpa.chat.repository;

import com.talentmarket.KmongJpa.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {

//    Optional<ChatRoom> findByUser1IdAndUser2Id(Long user1Id, Long user2Id);
//    Optional<ChatRoom> findByUser2IdAndUser1Id(Long user1Id, Long user2Id);
@Query("SELECT c FROM ChatRoom c " +
        "WHERE (c.user1.id = :user1Id AND c.user2.id = :user2Id)" +
    "OR (c.user1.id = :user2Id AND c.user2.id = :user1Id)")
    Optional<ChatRoom> findChatUser(@Param("user1Id")Long user1Id, @Param("user2Id")Long user2Id);
    @Query("SELECT c from ChatRoom c where c.user1.id = :userId or c.user2.id = :userId")
    List<ChatRoom> findChatRoom(@Param("userId") Long userId);
}
