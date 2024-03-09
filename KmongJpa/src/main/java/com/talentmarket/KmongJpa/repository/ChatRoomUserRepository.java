package com.talentmarket.KmongJpa.repository;

import com.talentmarket.KmongJpa.entity.ChatRoomUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser,Long> {
}
