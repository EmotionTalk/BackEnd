package com.tutorial.backend.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

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
public class FileMessage implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "file_id", nullable = false)
    private File file;

    @Id
    private String messageId; // String으로 변경

    // Getters and setters
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getMessageId() { // Long에서 String으로 변경
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Builder
    public FileMessage(File file, String messageId) {
        this.file = file;
        this.messageId = messageId;
    }
}
