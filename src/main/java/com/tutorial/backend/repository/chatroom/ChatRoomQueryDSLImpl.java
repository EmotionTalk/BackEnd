package com.tutorial.backend.repository.chatroom;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import static com.tutorial.backend.entity.QChatRoom.chatRoom;

import com.tutorial.backend.controller.dto.ChatRoomAndFriendDto;
import com.tutorial.backend.entity.QChatRoom;
import com.tutorial.backend.entity.QMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Slf4j
@Repository
@RequiredArgsConstructor
public class ChatRoomQueryDSLImpl implements ChatRoomQueryDSL{

    private final JPAQueryFactory query;
    @Override
    public Optional<Long> findByMyIdAndFriendId(Long userId, Long friendId) {
        return Optional.ofNullable(
            query.select(chatRoom.id)
                .from(chatRoom)
                .where(
                    chatRoom.participant1.id.eq(userId).and(chatRoom.participant2.id.eq(friendId))
                        .or(chatRoom.participant1.id.eq(friendId).and(chatRoom.participant2.id.eq(userId)))
                )
                .fetchOne()
        );
    }

    @Override
    public List<ChatRoomAndFriendDto> findByMyId(Long userId) {
        QChatRoom chatRoom = QChatRoom.chatRoom;
        QMember participant1 = new QMember("participant1");
        QMember participant2 = new QMember("participant2");

        return query
                .select(Projections.constructor(
                        ChatRoomAndFriendDto.class, // DTO 생성자 사용
                        chatRoom.id, // 채팅방 ID
                        // CASE 문을 사용하여 조건에 맞는 친구 이름 선택
                        Expressions.stringTemplate("case when {0} then {1} else {2} end",
                                chatRoom.participant1.id.eq(userId),
                                participant2.memberName, // 현재 사용자가 participant1인 경우 participant2 이름
                                participant1.memberName // 현재 사용자가 participant2인 경우 participant1 이름
                        ),
                        chatRoom.lastChatTime, // 마지막 채팅 시간
                        chatRoom.lastChat, // 마지막 채팅 내용
                        // CASE 문을 사용하여 조건에 맞는 프로필 이미지 URL 선택
                        Expressions.stringTemplate("case when {0} then {1} else {2} end",
                                chatRoom.participant1.id.eq(userId),
                                participant2.profileImageUrl, // 현재 사용자가 participant1인 경우 participant2 프로필 URL
                                participant1.profileImageUrl // 현재 사용자가 participant2인 경우 participant1 프로필 URL
                        )
                ))
                .from(chatRoom)
                .join(chatRoom.participant1, participant1)
                .join(chatRoom.participant2, participant2)
                .where(
                        chatRoom.participant1.id.eq(userId)
                                .or(chatRoom.participant2.id.eq(userId)) // or 조건: participant1 또는 participant2가 userId인 경우
                )
                .orderBy(chatRoom.lastChatTime.desc()) // 마지막 채팅 시간 기준 내림차순 정렬
                .fetch();
    }
}
