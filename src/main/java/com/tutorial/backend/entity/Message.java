package com.tutorial.backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tutorial.backend.entity.type.StatusType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "tbl_message")
@Getter @Setter
@NoArgsConstructor
@ToString
public class Message {
    @Id
    private String id;

    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime sendTime;

    private int readCount;

    private String emotion;

    private String status;

    private String type;

    private Long memberId;

    private Long chatRoomId;

    private StatusType isDeleted;

    @Builder
     public Message(String id, String content, LocalDateTime sendTime, int readCount, String emotion, String status, String type, Long memberId, Long chatRoomId, StatusType isDeleted) {
        this.id = id;
        this.content = content;
        this.sendTime = sendTime;
        this.readCount = readCount;
        this.emotion = emotion;
        this.status = status;
        this.type = type;
        this.memberId = memberId;
        this.chatRoomId = chatRoomId;
        this.isDeleted = isDeleted;
    }
}
