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
    private String fileContent; // 파일 내용을 Base64로 인코딩하여 보냄
    private String fileName; // 파일 이름
    @Builder
    public ChatFileMessageDto(String message, Long senderId, Long chatRoomId, Long fileId, MessageType messageType, String fileContent, String fileName) {
        this.message = message;
        this.senderId = senderId;
        this.chatRoomId = chatRoomId;
        this.fileId = fileId;
        this.messageType = messageType;
        this.fileContent = fileContent;
        this.fileName = fileName;
    }
}
