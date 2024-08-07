package com.tutorial.backend.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFileMessage is a Querydsl query type for FileMessage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFileMessage extends EntityPathBase<FileMessage> {

    private static final long serialVersionUID = -376479363L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFileMessage fileMessage = new QFileMessage("fileMessage");

    public final QFile file;

    public final StringPath messageId = createString("messageId");

    public QFileMessage(String variable) {
        this(FileMessage.class, forVariable(variable), INITS);
    }

    public QFileMessage(Path<? extends FileMessage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFileMessage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFileMessage(PathMetadata metadata, PathInits inits) {
        this(FileMessage.class, metadata, inits);
    }

    public QFileMessage(Class<? extends FileMessage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.file = inits.isInitialized("file") ? new QFile(forProperty("file")) : null;
    }

}

