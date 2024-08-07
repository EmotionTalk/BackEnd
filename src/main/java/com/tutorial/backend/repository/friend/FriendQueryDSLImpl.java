package com.tutorial.backend.repository.friend;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FriendQueryDSLImpl implements FriendQueryDSL {

    private final JPAQueryFactory query;

}
