package com.tutorial.backend.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "tbl_chatroom")
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant1_id", nullable = false)
    private Member participant1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant2_id", nullable = false)
    private Member participant2;

    private String lastChat;

    private LocalDateTime lastChatTime;

    @Builder
    public ChatRoom(Long id, Member participant1, Member participant2, String lastChat, LocalDateTime lastChatTime) {
        this.id = id;
        this.participant1 = participant1;
        this.participant2 = participant2;
        this.lastChat = lastChat;
        this.lastChatTime = lastChatTime;
    }
}
