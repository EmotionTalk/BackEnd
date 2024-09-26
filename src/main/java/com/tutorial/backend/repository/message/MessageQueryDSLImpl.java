package com.tutorial.backend.repository.message;

import com.tutorial.backend.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
@RequiredArgsConstructor
public class MessageQueryDSLImpl implements MessageQueryDSL{

    private final MongoTemplate mongoTemplate;


    @Override
    public Message findLastMessageByChatRoomId(Long chatRoomId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("chatRoomId").is(chatRoomId)); // chatRoomId 필터링
        query.with(Sort.by(Sort.Direction.DESC, "sendTime")); // sendTime으로 내림차순 정렬
        query.limit(1); // 최신 메시지 하나만 가져오기

        return mongoTemplate.findOne(query, Message.class);
    }
}
