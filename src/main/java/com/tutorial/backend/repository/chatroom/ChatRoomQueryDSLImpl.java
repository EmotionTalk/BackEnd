package com.tutorial.backend.repository.chatroom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import static com.tutorial.backend.entity.QChatRoom.chatRoom;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

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

}
