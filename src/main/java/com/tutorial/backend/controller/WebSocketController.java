package com.tutorial.backend.controller;

import com.tutorial.backend.controller.dto.ChatFileMessageDto;
import com.tutorial.backend.controller.dto.ChatMessageDto;
import com.tutorial.backend.entity.File;
import com.tutorial.backend.entity.Message;
import com.tutorial.backend.entity.type.MessageType;
import com.tutorial.backend.entity.type.StatusType;
import com.tutorial.backend.provider.MemberDetail;
import com.tutorial.backend.service.file.FileService;
import com.tutorial.backend.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebSocketController {

    private final MessageService messageService;
    private final FileService fileService;
    private final SimpMessagingTemplate messagingTemplate;

    // DTO를 엔티티로 변환하는 메서드
    private Message convertToEntity(ChatMessageDto messageDto) {
        return Message.builder()
                .id(messageDto.getId())
                .content(messageDto.getMessage())
                .sendTime(LocalDateTime.now())
                .readCount(0)
                .emotionNum(messageDto.getEmotionNum())
                .status("ACTIVE")
                .type(messageDto.getMessageType().name())
                .memberId(messageDto.getSenderId())
                .chatRoomId(messageDto.getChatRoomId())
                .build();
    }

    private Message convertToEntity(ChatFileMessageDto messageDto) {
        return Message.builder()
                .id(messageDto.getId())
                .content(messageDto.getMessage())
                .sendTime(LocalDateTime.now())
                .readCount(0)
                .emotionNum(messageDto.getEmotionNum())
                .status("ACTIVE")
                .type(messageDto.getMessageType().name())
                .memberId(messageDto.getSenderId())
                .chatRoomId(messageDto.getChatRoomId())
                .build();
    }
    @MessageMapping("/file-message/{chatRoomId}")
    public void receiveFileMessage(@DestinationVariable Long chatRoomId, @Payload ChatFileMessageDto fileMessageDto, Authentication authentication) {
        log.info("File message received: {}", fileMessageDto);
        MemberDetail principal = (MemberDetail) authentication.getPrincipal();

        try {
            Long fileId = fileMessageDto.getFileId();
            File file = fileService.getFileById(fileId); // 파일 정보를 조회
            log.info(fileMessageDto.getMessageType().name());
            if (file != null) {
                // 메시지 저장
                Message message = Message.builder()
                            .content(fileMessageDto.getMessage()) // 메시지 내용 설정
                            .sendTime(LocalDateTime.now()) // 현재 시간 설정
                            .type(fileMessageDto.getMessageType().name()) // 메시지 타입 설정
                            .emotionNum(fileMessageDto.getEmotionNum())
                            .memberId(principal.getId()) // 발신자 ID 설정
                            .chatRoomId(chatRoomId) // 채팅방 ID 설정
                            .isDeleted(StatusType.ABLE)
                            .build();

                // 메시지 저장
                Message savedMessage = messageService.saveMessage(message);

                // FileMessage 엔티티 생성 및 저장
                messageService.saveFileMessage(file, savedMessage.getId());

                // 채팅방의 구독자들에게 메시지 전송
                ChatMessageDto chatMessageDto = ChatMessageDto.builder()
                        .id(savedMessage.getId())
                        .message(message.getContent()) // 메시지 내용
                        .sendTime(message.getSendTime()) // 메시지 전송 시간
                        .messageType(MessageType.valueOf(message.getType())) // 메시지 타입
                        .emotionNum(message.getEmotionNum())
                        .senderId(message.getMemberId()) // 발신자 ID
                        .isDeleted(message.getIsDeleted()) // 삭제 여부
                        .filePath(file.getFilePath()) // 파일 경로
                        .chatRoomId(message.getChatRoomId()) // 채팅방 ID
                        .build();
                log.info(chatMessageDto.toString());
                messagingTemplate.convertAndSend("/sub/chatroom/" + chatRoomId, chatMessageDto);
            }
        } catch (Exception e) {
            log.error("Error processing file message", e);
        }
    }

    // 기존 메시지 전송
    @MessageMapping("/message/{chatRoomId}")
    public void receiveMessage(@DestinationVariable Long chatRoomId, @Payload ChatMessageDto messageDto, Authentication authentication) {
        MemberDetail principal = (MemberDetail) authentication.getPrincipal();
        Long id = principal.getId();
        messageDto.setSenderId(id);
        messageDto.setIsDeleted(StatusType.ABLE);

        // 메시지 저장
        Message chatMessage = messageService.saveMessage(convertToEntity(messageDto));

        messageDto.setId(chatMessage.getId());
        // 메시지를 해당 채팅방 구독자들에게 전송
        messagingTemplate.convertAndSend("/sub/chatroom/" + chatRoomId, messageDto);
    }
}
