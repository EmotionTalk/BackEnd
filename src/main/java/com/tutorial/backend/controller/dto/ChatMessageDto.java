package com.tutorial.backend.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tutorial.backend.entity.type.MessageType;
import com.tutorial.backend.entity.type.StatusType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ChatMessageDto {

    private String id;

    private MessageType messageType; // 메시지 타입

    private Long chatRoomId; // 방 번호

    private Long senderId; // 채팅을 보낸 사람

    private String message; // 메시지

    private int emotionNum;

    private String filePath;

    private StatusType isDeleted;

    private String aiSuggestion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime sendTime; // 전송한 시간

    @Builder
    public ChatMessageDto(String id, MessageType messageType, Long chatRoomId, Long senderId, String message, int emotionNum, String filePath, StatusType isDeleted, String aiSuggestion, LocalDateTime sendTime) {
        this.id = id;
        this.messageType = messageType;
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
        this.message = message;
        this.emotionNum = emotionNum;
        this.filePath = filePath;
        this.isDeleted = isDeleted;
        this.aiSuggestion = aiSuggestion;
        this.sendTime = sendTime;
    }
}