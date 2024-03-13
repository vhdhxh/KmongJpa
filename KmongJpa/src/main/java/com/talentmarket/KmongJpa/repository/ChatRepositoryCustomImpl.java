package com.talentmarket.KmongJpa.repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.talentmarket.KmongJpa.entity.Chat;
import com.talentmarket.KmongJpa.entity.QChat;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
public class ChatRepositoryCustomImpl implements ChatRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;
    QChat chat = QChat.chat;

//    public List<Chat> findChatList (List<Long> chatRoomIdList) {
//        List<Chat> result = jpaQueryFactory
//                .selectFrom(chat)
//                .where(chat.id.in(
//                        JPAExpressions
//                                .select(chat.id.max())
//                                .from(chat)
//                                .groupBy(chat.chatRoom.id)
//                ))
//                .fetch();
//        return result;
public List<Chat> findChatList (List<Long> chatRoomIdList) {
//    List<Chat> result = jpaQueryFactory
//            .selectFrom(chat)
//            .where(chat.chatRoom.id.in(chatRoomIdList))
//            .fetch();
    List<Chat> result = entityManager.createQuery(
                    "SELECT c FROM Chat c WHERE c.id IN (" +
                            "SELECT MAX(c2.id) FROM Chat c2 WHERE c2.chatRoom.id IN :chatRoomIdList GROUP BY c2.chatRoom.id" +
                            ")", Chat.class)
            .setParameter("chatRoomIdList", chatRoomIdList)
            .getResultList();

    return result;
    }
}