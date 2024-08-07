package com.tutorial.backend.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 934286536L;

    public static final QMember member = new QMember("member1");

    public final ListPath<Friend, QFriend> addedFriends = this.<Friend, QFriend>createList("addedFriends", Friend.class, QFriend.class, PathInits.DIRECT2);

    public final EnumPath<com.tutorial.backend.entity.type.Authority> authority = createEnum("authority", com.tutorial.backend.entity.type.Authority.class);

    public final ListPath<ChatRoom, QChatRoom> chatRoomsAsParticipant1 = this.<ChatRoom, QChatRoom>createList("chatRoomsAsParticipant1", ChatRoom.class, QChatRoom.class, PathInits.DIRECT2);

    public final ListPath<ChatRoom, QChatRoom> chatRoomsAsParticipant2 = this.<ChatRoom, QChatRoom>createList("chatRoomsAsParticipant2", ChatRoom.class, QChatRoom.class, PathInits.DIRECT2);

    public final ListPath<Friend, QFriend> friends = this.<Friend, QFriend>createList("friends", Friend.class, QFriend.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath memberEmail = createString("memberEmail");

    public final StringPath memberName = createString("memberName");

    public final StringPath memberPhone = createString("memberPhone");

    public final StringPath profileImageUrl = createString("profileImageUrl");

    public final EnumPath<com.tutorial.backend.entity.type.StatusType> status = createEnum("status", com.tutorial.backend.entity.type.StatusType.class);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

