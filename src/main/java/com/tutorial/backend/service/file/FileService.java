package com.tutorial.backend.service.file;

import com.tutorial.backend.entity.File;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface FileService {
    public File uploadFile(MultipartFile multipartFile) throws IOException;
    public File getFileById(Long fileId);
    public File getFileByOriginName(String fileName);
}
