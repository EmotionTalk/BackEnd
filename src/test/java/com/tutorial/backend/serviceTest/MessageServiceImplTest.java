package com.tutorial.backend.serviceTest;

import com.tutorial.backend.entity.Message;
import com.tutorial.backend.entity.type.StatusType;
import com.tutorial.backend.service.message.MessageServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
@Slf4j
public class MessageServiceImplTest {

    @Autowired
    private MessageServiceImpl messageServiceImpl;

    @Test
    public void saveTest(){
        Message message = new Message();
        message.setChatRoomId(2L);
        message.setMemberId(2L);
        message.setContent("룰루조아");
        message.setEmotion("기쁨");
        message.setType("Text");
        message.setStatus(StatusType.ABLE.name());
        message.setReadCount(1);
        message.setSendTime(LocalDateTime.now());
       messageServiceImpl.saveMessage(message);
    }
    @Test
    public void timeTest(){
        log.info(LocalDateTime.now().toString());
    }
}
