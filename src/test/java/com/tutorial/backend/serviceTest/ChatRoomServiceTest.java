package com.tutorial.backend.serviceTest;

import com.tutorial.backend.service.chatroom.ChatRoomService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ChatRoomServiceTest {
    @Autowired
    private  ChatRoomService chatRoomService;

    @Test
    public void findMyChatRooms(){
        log.info(chatRoomService.getMyChatRoomAndFriend(1L).toString());
    }
}
