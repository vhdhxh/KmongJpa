package com.talentmarket.KmongJpa.chat.repository;

import com.talentmarket.KmongJpa.chat.domain.Chat;

import java.util.List;

public interface ChatRepositoryCustom {
    List<Chat> findChatList ( List<Long> chatRoomIdList);
}
