package com.tutorial.backend.service.message;

import com.tutorial.backend.entity.File;
import com.tutorial.backend.entity.FileMessage;
import com.tutorial.backend.entity.Message;
import com.tutorial.backend.repository.FileMessage.FileMessageRepository;
import com.tutorial.backend.repository.message.MessageRepository;
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

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public void saveFileMessage(File file, Long messageId) {
        FileMessage fileMessage = FileMessage.builder()
                .file(file)
                .messageId(messageId)
                .build();

        fileMessageRepository.save(fileMessage);
    }

    public List<Message> getMessagesByChatRoomId(Long chatRoomId) {
        return messageRepository.findByChatRoomId(chatRoomId);
    }

}

