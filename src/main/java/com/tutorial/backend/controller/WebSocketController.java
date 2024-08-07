package com.tutorial.backend.controller;

import com.tutorial.backend.controller.dto.ChatFileMessageDto;
import com.tutorial.backend.controller.dto.ChatMessageDto;
import com.tutorial.backend.converter.ByteArrayMultipartFile;
import com.tutorial.backend.entity.File;
import com.tutorial.backend.entity.Message;
import com.tutorial.backend.entity.type.MessageType;
import com.tutorial.backend.provider.MemberDetail;
import com.tutorial.backend.service.file.FileService;
import com.tutorial.backend.service.message.MessageServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebSocketController {

    private final MessageServiceImpl messageServiceImpl;
    private final FileService fileService;
    private final SimpMessagingTemplate messagingTemplate;

    // DTO를 엔티티로 변환하는 메서드
    private Message convertToEntity(ChatMessageDto messageDto) {
        return Message.builder()
                .content(messageDto.getMessage())
                .sendTime(LocalDateTime.now())
                .readCount(0)
                .emotion("")
                .status("ACTIVE")
                .type(messageDto.getMessageType().name())
                .memberId(messageDto.getSenderId())
                .chatRoomId(messageDto.getChatRoomId())
                .build();
    }

    private Message convertToEntity(ChatFileMessageDto messageDto) {
        return Message.builder()
                .content(messageDto.getMessage())
                .sendTime(LocalDateTime.now())
                .readCount(0)
                .emotion("")
                .status("ACTIVE")
                .type(messageDto.getMessageType().name())
                .memberId(messageDto.getSenderId())
                .chatRoomId(messageDto.getChatRoomId())
                .build();
    }

    @MessageMapping("/file-message/{chatRoomId}")
    public void receiveFileMessage(@DestinationVariable Long chatRoomId, @Payload ChatFileMessageDto fileMessageDto, Authentication authentication, ChatMessageDto messageDto) {

        log.info("File message received: {}", fileMessageDto);
        MemberDetail principal = (MemberDetail) authentication.getPrincipal();
        messageDto.setSenderId( principal.getId());

        try {
            String fileContent = fileMessageDto.getFileContent();
            String fileName = fileMessageDto.getFileName();

            if (fileContent != null && !fileContent.isEmpty()) {
                byte[] fileBytes = Base64.getDecoder().decode(fileContent);

                ByteArrayMultipartFile multipartFile = new ByteArrayMultipartFile(fileName, fileBytes);


                // 파일 업로드
                File uploadedFile = fileService.uploadFile(multipartFile);
                log.info("File uploaded: {}", uploadedFile.getFilePath());

                // ChatMessageDto 생성 및 설정
                ChatMessageDto chatMessageDto = ChatMessageDto.builder()
                        .message("File uploaded")
                        .sendTime(LocalDateTime.now())
                        .messageType(MessageType.FILE) // 메시지 타입을 FILE로 설정
                        .filePath(uploadedFile.getFilePath()) // 업로드된 파일의 경로 설정
                        .chatRoomId(chatRoomId)
                        .build();

                // 메시지 저장 (옵션)
                messageServiceImpl.saveMessage(convertToEntity(chatMessageDto));

                // 파일 메시지를 해당 채팅방 구독자들에게 전송
                messagingTemplate.convertAndSend("/sub/chatroom/" + chatRoomId, chatMessageDto);
            }
        } catch (IOException e) {
            log.error("Error uploading file and sending message", e);
        }
    }

    // 기존 메시지 전송
    @MessageMapping("/message/{chatRoomId}")
    public void receiveMessage(@DestinationVariable Long chatRoomId, @Payload ChatMessageDto messageDto, Authentication authentication) {
        MemberDetail principal = (MemberDetail) authentication.getPrincipal();
        Long id = principal.getId();
        messageDto.setSenderId(id);

        // 메시지 저장
        Message chatMessage = messageServiceImpl.saveMessage(convertToEntity(messageDto));

        // 메시지를 해당 채팅방 구독자들에게 전송
        messagingTemplate.convertAndSend("/sub/chatroom/" + chatRoomId, messageDto);
    }
}
