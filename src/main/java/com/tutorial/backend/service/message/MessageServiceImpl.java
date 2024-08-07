package com.tutorial.backend.service.message;

import com.tutorial.backend.entity.File;
import com.tutorial.backend.entity.FileMessage;
import com.tutorial.backend.entity.Message;
import com.tutorial.backend.repository.FileMessage.FileMessageRepository;
import com.tutorial.backend.repository.message.MessageRepository;
import com.tutorial.backend.service.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService{

    private final MessageRepository messageRepository;
    private final FileMessageRepository fileMessageRepository;
    private final FileService fileService;

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public void saveFileMessage(File file, String messageId) {
        FileMessage fileMessage = FileMessage.builder()
                .file(file)
                .messageId(messageId)
                .build();

        fileMessageRepository.save(fileMessage);
    }

    public List<Message> getMessagesByChatRoomId(Long chatRoomId) {
        return messageRepository.findByChatRoomId(chatRoomId);
    }

    public List<FileMessage> getFileMessagesByMessageId(String messageId) {
        return fileMessageRepository.findByMessageId(messageId);
    }
}
