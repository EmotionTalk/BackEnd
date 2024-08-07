package com.tutorial.backend.repository.chatroom;

import com.tutorial.backend.entity.ChatRoom;
import com.tutorial.backend.repository.friend.FriendQueryDSL;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, ChatRoomQueryDSL {
}
