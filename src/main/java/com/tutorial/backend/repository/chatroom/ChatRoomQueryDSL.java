package com.tutorial.backend.repository.chatroom;

import com.tutorial.backend.controller.dto.ChatRoomAndFriendDto;
import com.tutorial.backend.entity.ChatRoom;

import java.util.List;
import java.util.Optional;

public interface ChatRoomQueryDSL {
    Optional<Long> findByMyIdAndFriendId(Long myId, Long friendId);

    List<ChatRoomAndFriendDto> findByMyId(Long id);

}
