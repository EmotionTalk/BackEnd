package com.tutorial.backend.controller;

import com.tutorial.backend.controller.dto.ChatMessageDto;
import com.tutorial.backend.controller.dto.ChatRoomAndFriendDto;
import com.tutorial.backend.controller.dto.ChatRoomWithMessagesDto;
import com.tutorial.backend.controller.dto.ResultDto;
import com.tutorial.backend.entity.File;
import com.tutorial.backend.entity.FileMessage;
import com.tutorial.backend.entity.Message;
import com.tutorial.backend.entity.type.MessageType;
import com.tutorial.backend.provider.MemberDetail;
import com.tutorial.backend.service.chatroom.ChatRoomService;
import com.tutorial.backend.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chatroom/*")
@RequiredArgsConstructor
@Slf4j
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final MessageService messageService;


    @GetMapping("getOrMakeChatRoom")
    public ResponseEntity<ResultDto<ChatRoomWithMessagesDto>> getOrMakeChatRoom(Authentication authentication, @RequestParam Long friendId) {
        MemberDetail principal = (MemberDetail) authentication.getPrincipal();
        Optional<Long> chatRoomId = chatRoomService.getChatRoom(principal.getId(), friendId);
        Long finalChatRoomId = chatRoomId.orElseGet(() -> chatRoomService.newRoom(principal.getMember(), friendId));

        List<Message> messages = messageService.getMessagesByChatRoomId(finalChatRoomId);
        log.info(messages.toString());
        List<ChatMessageDto> messageDtos = messages.stream()
                .map(message -> {
                    ChatMessageDto dto = toDto(message);
                    if (message.getType().equals(MessageType.IMAGE.name())) {
                        List<FileMessage> fileMessages = messageService.getFileMessagesByMessageId(message.getId());
                        if (!fileMessages.isEmpty()) {
                            File file = fileMessages.get(0).getFile();
                            log.info(file.toString());
                            dto.setFilePath(file.getFilePath());
                        }
                    }
                    return dto;
                })
                .collect(Collectors.toList());

        ChatRoomWithMessagesDto responseDto = ChatRoomWithMessagesDto.builder()
                .chatRoomId(finalChatRoomId)
                .userId(principal.getId())
                .messages(messageDtos)
                .build();
        return ResponseEntity.ok().body(ResultDto.res(HttpStatus.ACCEPTED, chatRoomId.isPresent() ? "채팅방을 불러왔습니다!" : "새로운 채팅방이 생성되었습니다!", responseDto));
    }

    @GetMapping("getChatRoom")
    public ResponseEntity<ResultDto<List<ChatRoomAndFriendDto>>> getChatRoom(Authentication authentication) {
        MemberDetail principal = (MemberDetail) authentication.getPrincipal();
        Long myId = principal.getId();

        // 채팅방 목록 가져오기
        List<ChatRoomAndFriendDto> chatRoomAndFriendDtoList = chatRoomService.getMyChatRoomAndFriend(myId);

        // 각 채팅방에 대한 마지막 메시지를 가져와 설정
        chatRoomAndFriendDtoList.forEach(chatRoomAndFriendDto -> {
            // 채팅방의 마지막 메시지 가져오기
            Message lastMessage = messageService.getLastMessageByChatRoomId(chatRoomAndFriendDto.getChatRoomId());
            if (lastMessage != null) {
                // 마지막 메시지의 내용 설정
                chatRoomAndFriendDto.setLastChat(lastMessage.getContent());
                chatRoomAndFriendDto.setSendTime(lastMessage.getSendTime());
            }
        });

        return ResponseEntity.ok().body(ResultDto.res(HttpStatus.ACCEPTED, "채팅방을 불러왔습니다!", chatRoomAndFriendDtoList));
    }

        private ChatMessageDto toDto(Message message) {
        return ChatMessageDto.builder()
                .id(message.getId())
                .messageType(MessageType.valueOf(message.getType()))
                .emotionNum(message.getEmotionNum())
                .chatRoomId(message.getChatRoomId())
                .senderId(message.getMemberId())
                .message(message.getContent())
                .aiSuggestion(message.getAiSuggestion())
                .sendTime(message.getSendTime())
                .build();
    }

}
