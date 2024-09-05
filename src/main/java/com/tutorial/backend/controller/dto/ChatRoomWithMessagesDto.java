package com.tutorial.backend.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ChatRoomWithMessagesDto {
    private Long chatRoomId;
    private Long userId;
    private List<ChatMessageDto> messages;

    @Builder
    public ChatRoomWithMessagesDto(Long chatRoomId,Long userId ,List<ChatMessageDto> messages) {
        this.chatRoomId = chatRoomId;
        this.userId = userId;
        this.messages = messages;
    }

}
