package com.tutorial.backend.queryDSLTest;

import com.tutorial.backend.service.message.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class MessageServiceTest {
    @Autowired
    MessageService messageService;

    @Test
    public void findMessageById(){

        log.info(messageService.getMessageById("66e682bd4d566538eb89c447").toString());
    }

}
