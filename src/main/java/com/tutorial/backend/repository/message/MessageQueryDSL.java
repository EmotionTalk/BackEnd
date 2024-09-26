package com.tutorial.backend.repository.message;

import com.tutorial.backend.entity.Message;

public interface MessageQueryDSL {
    Message findLastMessageByChatRoomId(Long chatRoomId);
}
