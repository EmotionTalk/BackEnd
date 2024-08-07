package com.tutorial.backend.controller;

import com.tutorial.backend.controller.dto.ChatMessageDto;
import com.tutorial.backend.controller.dto.ChatRoomWithMessagesDto;
import com.tutorial.backend.controller.dto.ResultDto;
import com.tutorial.backend.entity.Message;
import com.tutorial.backend.entity.type.MessageType;
import com.tutorial.backend.provider.MemberDetail;
import com.tutorial.backend.service.chatroom.ChatRoomService;
import com.tutorial.backend.service.message.MessageServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chatroom/*")
@RequiredArgsConstructor
@Slf4j
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final MessageServiceImpl messageServiceImpl;

    @GetMapping("getOrMakeChatRoom")
    public ResponseEntity<ResultDto<ChatRoomWithMessagesDto>> getChatRoom(Authentication authentication, @RequestParam Long friendId) {
        MemberDetail principal = (MemberDetail) authentication.getPrincipal();
        log.info("myId : " + principal.getId() + " friendId : " + friendId);
        Optional<Long> chatRoomId = chatRoomService.getChatRoom(principal.getId(), friendId);
        if (chatRoomId.isPresent()) {
            log.info(chatRoomId.get().toString());
            List<Message> messages = messageServiceImpl.getMessagesByChatRoomId(chatRoomId.get());

            List<ChatMessageDto> messageDtos = messages.stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

            ChatRoomWithMessagesDto responseDto = ChatRoomWithMessagesDto.builder()
                    .chatRoomId(chatRoomId.get())
                    .messages(messageDtos)
                    .build();
            return ResponseEntity.ok().body(ResultDto.res(HttpStatus.ACCEPTED, "채팅방을 불러왔습니다!", responseDto));
        } else {
            Long newChatRoom = chatRoomService.newRoom(principal.getMember(), friendId);
            List<Message> messages = messageServiceImpl.getMessagesByChatRoomId(newChatRoom);
            List<ChatMessageDto> messageDtos = messages.stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
            ChatRoomWithMessagesDto responseDto = ChatRoomWithMessagesDto.builder()
                    .chatRoomId(newChatRoom)
                    .messages(messageDtos)
                    .build();
            return ResponseEntity.ok().body(ResultDto.res(HttpStatus.ACCEPTED, "새로운 채팅방이 생성되었습니다!", responseDto));
        }
    }

    private ChatMessageDto toDto(Message message) {
        return ChatMessageDto.builder()
                .messageType(MessageType.TEXT) // Assuming all messages are of type TALK for this example
                .chatRoomId(message.getChatRoomId())
                .senderId(message.getMemberId())
                .message(message.getContent())
                .sendTime(message.getSendTime())
                .build();
    }
}
