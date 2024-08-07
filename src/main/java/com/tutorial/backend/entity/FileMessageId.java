package com.tutorial.backend.entity;
import java.io.Serializable;
import java.util.Objects;
import java.io.Serializable;
import java.util.Objects;

public class FileMessageId implements Serializable {
    private Long file;
    private String messageId; // Long에서 String으로 변경

    // Default constructor
    public FileMessageId() {}

    // Constructor with parameters
    public FileMessageId(Long file, String messageId) {
        this.file = file;
        this.messageId = messageId;
    }

    // Getters and setters

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileMessageId that = (FileMessageId) o;
        return file == that.file && Objects.equals(messageId, that.messageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, messageId);
    }
}
