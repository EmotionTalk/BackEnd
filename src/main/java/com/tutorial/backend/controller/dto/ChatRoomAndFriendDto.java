package com.tutorial.backend.controller.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ChatRoomAndFriendDto {
    Long chatRoomId;
    Long friendId;
    String nickName;
    LocalDateTime sendTime;
    String lastChat;
    String friendProfileUrl;

    @Builder
    public ChatRoomAndFriendDto(Long chatRoomId, Long friendId, String nickName, LocalDateTime sendTime, String lastChat, String friendProfileUrl) {
        this.chatRoomId = chatRoomId;
        this.friendId = friendId;
        this.nickName = nickName;
        this.sendTime = sendTime;
        this.lastChat = lastChat;
        this.friendProfileUrl = friendProfileUrl;
    }
}
