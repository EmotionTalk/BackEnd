package com.tutorial.backend.service.chatroom;

import com.tutorial.backend.controller.dto.ChatRoomAndFriendDto;
import com.tutorial.backend.entity.Member;

import java.util.List;
import java.util.Optional;

public interface ChatRoomService {


    Optional<Long> getChatRoom(Long id, Long friendId);

    List<ChatRoomAndFriendDto> getMyChatRoomAndFriend(Long id);

    Long newRoom(Member me, Long friendId);
}
