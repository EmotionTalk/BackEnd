package com.tutorial.backend.service.message;

import com.tutorial.backend.entity.File;
import com.tutorial.backend.entity.FileMessage;
import com.tutorial.backend.entity.Message;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface MessageService {

    public Message saveMessage(Message message);

    public void saveFileMessage(File file, String messageId);

    public List<Message> getMessagesByChatRoomId(Long chatRoomId);
    public List<FileMessage> getFileMessagesByMessageId(String messageId);

    Message getLastMessageByChatRoomId(Long chatRoomId);

    Optional<Message> getMessageById(String id);
}
