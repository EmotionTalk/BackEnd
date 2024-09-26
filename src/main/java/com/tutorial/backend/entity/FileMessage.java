package com.tutorial.backend.entity;

import lombok.*;
import org.bson.types.ObjectId;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@Table(name = "tbl_file_message")
@IdClass(FileMessageId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class FileMessage implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "file_id", nullable = false)
    private File file;

    @Id
    private String messageId; // String으로 변경

    @Builder
    public FileMessage(File file, String messageId) {
        this.file = file;
        this.messageId = messageId;
    }
}
