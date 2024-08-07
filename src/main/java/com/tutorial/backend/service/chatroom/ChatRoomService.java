package com.tutorial.backend.service.chatroom;

import com.tutorial.backend.entity.Member;

import java.util.Optional;

public interface ChatRoomService {


    Optional<Long> getChatRoom(Long id, Long friendId);

    Long newRoom(Member me, Long friendId);
}
