package com.talentmarket.KmongJpa.repository;

import com.talentmarket.KmongJpa.entity.Chat;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepositoryCustom {
    List<Chat> findChatList ( List<Long> chatRoomIdList);
}
