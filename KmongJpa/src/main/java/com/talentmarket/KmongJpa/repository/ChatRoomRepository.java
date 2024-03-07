package com.talentmarket.KmongJpa.repository;

import com.talentmarket.KmongJpa.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {

    Optional<ChatRoom> findByUser1IdAndUser2Id(Long user1Id, Long user2Id);
    Optional<ChatRoom> findByUser2IdAndUser1Id(Long user1Id, Long user2Id);

}
