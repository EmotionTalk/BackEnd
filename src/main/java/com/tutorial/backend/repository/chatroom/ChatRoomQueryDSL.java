package com.tutorial.backend.repository.chatroom;

import com.tutorial.backend.entity.ChatRoom;

import java.util.Optional;

public interface ChatRoomQueryDSL {
    Optional<Long> findByMyIdAndFriendId(Long myId, Long friendId);

}
