package com.tutorial.backend.service.file;

import com.tutorial.backend.entity.File;
import com.tutorial.backend.repository.file.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
@Slf4j
public class FileServiceImpl implements FileService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    private final FileRepository fileRepository;

    public File uploadFile(MultipartFile multipartFile) throws IOException {
        // Ensure the upload directory exists
        LocalDate now = LocalDate.now();
        String datePath = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        Path uploadPath = Paths.get(uploadDir, datePath);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // UUID로 이름을 바꿔 동일명을 피해 저장함
        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + "_" + multipartFile.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(multipartFile.getInputStream(), filePath);

        // Generate file URL
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/")
                .path(datePath + "/")
                .path(fileName)
                .toUriString();
        log.info("serviceimpl : " + fileDownloadUri);

        // Save file info to the database
        File file = File.builder()
                .uuid(uuid)
                .filePath(fileDownloadUri) // 절대 경로 URL 저장
                .fileType(multipartFile.getContentType())
                .fileOriginName(multipartFile.getOriginalFilename())
                .fileSize((int) multipartFile.getSize())
                .status("ACTIVE")
                .build();

        return fileRepository.save(file);
    }

    @Override
    public File getFileById(Long fileId) {
        return fileRepository.findById(fileId).orElse(null);
    }

    public File getFileByOriginName(String originName) {
        return fileRepository.findByFileOriginName(originName).orElse(null);
    }
}
