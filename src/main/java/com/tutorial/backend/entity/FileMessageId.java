package com.tutorial.backend.entity;
import java.io.Serializable;
import java.util.Objects;

public class FileMessageId implements Serializable {
    private Long file;
    private Long messageId;

    // Default constructor
    public FileMessageId() {}

    // Constructor with parameters
    public FileMessageId(Long file, Long messageId) {
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

