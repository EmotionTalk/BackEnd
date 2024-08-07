package com.tutorial.backend.service.message;

import com.tutorial.backend.entity.File;
import com.tutorial.backend.entity.Message;

import java.util.List;

public interface MessageService {

    public Message saveMessage(Message message);

    public void saveFileMessage(File file, Long messageId);

    public List<Message> getMessagesByChatRoomId(Long chatRoomId);

}
