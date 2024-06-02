package com.tutorial.backend.entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tbl_file_message")
@IdClass(FileMessageId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileMessage implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "file_id", nullable = false)
    private File file;

    private Long messageId;

    // Getters and setters
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }
}
