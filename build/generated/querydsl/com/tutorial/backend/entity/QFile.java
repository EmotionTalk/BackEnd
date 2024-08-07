package com.tutorial.backend.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFile is a Querydsl query type for File
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFile extends EntityPathBase<File> {

    private static final long serialVersionUID = -2135542998L;

    public static final QFile file = new QFile("file");

    public final com.tutorial.backend.aduit.QPeriod _super = new com.tutorial.backend.aduit.QPeriod(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final ListPath<FileMessage, QFileMessage> fileMessages = this.<FileMessage, QFileMessage>createList("fileMessages", FileMessage.class, QFileMessage.class, PathInits.DIRECT2);

    public final StringPath fileOriginName = createString("fileOriginName");

    public final StringPath filePath = createString("filePath");

    public final NumberPath<Integer> fileSize = createNumber("fileSize", Integer.class);

    public final StringPath fileType = createString("fileType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath status = createString("status");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final StringPath uuid = createString("uuid");

    public QFile(String variable) {
        super(File.class, forVariable(variable));
    }

    public QFile(Path<? extends File> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFile(PathMetadata metadata) {
        super(File.class, metadata);
    }

}

