package com.tutorial.backend.controller.dto;

import com.tutorial.backend.entity.type.MessageType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ChatFileMessageDto {
    private String message;
    private Long senderId;
    private Long chatRoomId;
    private Long fileId;
    private MessageType messageType;

    @Builder
    public ChatFileMessageDto(String message, Long senderId, Long chatRoomId, Long fileId, MessageType messageType) {
        this.message = message;
        this.senderId = senderId;
        this.chatRoomId = chatRoomId;
        this.fileId = fileId;
        this.messageType = messageType;
    }
}
