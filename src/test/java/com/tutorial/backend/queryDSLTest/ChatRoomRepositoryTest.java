package com.tutorial.backend.queryDSLTest;

import com.tutorial.backend.entity.ChatRoom;
import com.tutorial.backend.repository.chatroom.ChatRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Slf4j
public class ChatRoomRepositoryTest {
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Test
    @Transactional
    public void getMyChatRoomTest(){
    }

}
